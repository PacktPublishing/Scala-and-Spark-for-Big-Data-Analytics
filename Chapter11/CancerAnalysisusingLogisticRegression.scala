package com.chapter11.SparkMachineLearning

import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.classification.BinaryLogisticRegressionSummary
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.functions._

object CancerAnalysisusingLogisticRegression {
  case class Cancer(cancer_class: Double, thickness: Double, size: Double, shape: Double, madh: Double, epsize: Double, bnuc: Double, bchrom: Double, nNuc: Double, mit: Double)

  def parseCancer(line: Array[Double]): Cancer = {
    Cancer(if (line(9) == 4.0) 1 else 0, line(0), line(1), line(2), line(3), line(4), line(5), line(6), line(7), line(8))
  }

  def parseRDD(rdd: RDD[String]): RDD[Array[Double]] = {
    rdd.map(_.split(",")).filter(_(6) != "?").map(_.drop(1)).map(_.map(_.toDouble))
  }

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName("LogisticRegressionExample")
      .getOrCreate()

    val sqlContext = new SQLContext(spark.sparkContext)

    import sqlContext.implicits._
    import sqlContext._

    val rdd = spark.sparkContext.textFile("data/wbcd.csv")
    val cancerRDD = parseRDD(rdd).map(parseCancer)
    cancerRDD.collect().foreach(println)

    val cancerDF = cancerRDD.toDF().cache()
    //cancerDF.show()
    
    val featureCols = Array("thickness", "size", "shape", "madh", "epsize", "bnuc", "bchrom", "nNuc", "mit")
    val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")
    val df2 = assembler.transform(cancerDF)
    //df2.show()

    val labelIndexer = new StringIndexer().setInputCol("cancer_class").setOutputCol("label")
    val df3 = labelIndexer.fit(df2).transform(df2)
    df3.show()

    val splitSeed = 1234567
    val Array(trainingData, testData) = df3.randomSplit(Array(0.7, 0.3), splitSeed)

    val lr = new LogisticRegression().setMaxIter(50).setRegParam(0.01).setElasticNetParam(0.01)
    val model = lr.fit(trainingData)

    val predictions = model.transform(testData)
    predictions.show()

    //predictions.select("cancer_class", "label", "prediction").show(5)

    val trainingSummary = model.summary
    val objectiveHistory = trainingSummary.objectiveHistory
    objectiveHistory.foreach(loss => println(loss))

    val binarySummary = trainingSummary.asInstanceOf[BinaryLogisticRegressionSummary]

    // Obtain the receiver-operating characteristic as a dataframe and areaUnderROC.
    val roc = binarySummary.roc
    roc.show()
    println("Area Under ROC: " + binarySummary.areaUnderROC)

    // Calculate Metrics
    val lp = predictions.select("label", "prediction")
    val counttotal = predictions.count()
    val correct = lp.filter($"label" === $"prediction").count()
    val wrong = lp.filter(not($"label" === $"prediction")).count()
    val truep = lp.filter($"prediction" === 0.0).filter($"label" === $"prediction").count()
    val falseN = lp.filter($"prediction" === 0.0).filter(not($"label" === $"prediction")).count()
    val falseP = lp.filter($"prediction" === 1.0).filter(not($"label" === $"prediction")).count()
    val ratioWrong = wrong.toDouble / counttotal.toDouble
    val ratioCorrect = correct.toDouble / counttotal.toDouble

    println("Total Count: " + counttotal)
    println("Correctly Predicted: " + correct)
    println("Wrongly Identified: " + wrong)
    println("True Positive: " + truep)
    println("False Negative: " + falseN)
    println("False Positive: " + falseP)
    println("ratioWrong: " + ratioWrong)
    println("ratioCorrect: " + ratioCorrect)

    // Set the model threshold to maximize F-Measure
    val fMeasure = binarySummary.fMeasureByThreshold
    val fm = fMeasure.col("F-Measure")
    val maxFMeasure = fMeasure.select(max("F-Measure")).head().getDouble(0)
    val bestThreshold = fMeasure.where($"F-Measure" === maxFMeasure).select("threshold").head().getDouble(0)
    model.setThreshold(bestThreshold)

    val evaluator = new BinaryClassificationEvaluator().setLabelCol("label")
    val accuracy = evaluator.evaluate(predictions)
    println("Accuracy: " + accuracy)

    spark.stop()
  }
}

