package com.chapter12.NaiveBayes

import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}

/*
 * @author : Md. Rezaul Karim
 */

object NaiveBayesExample {
  def main(args: Array[String]): Unit = {    
    // Create the Spark session 
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()

    // Load the data stored in LIBSVM format as a DataFrame.
    val data = spark.read.format("libsvm").load("C:/Users/rezkar/Downloads/spark-2.1.0-bin-hadoop2.7/data/sample.data")

    // Split the data into training and test sets (30% held out for testing)
    val Array(trainingData, validationData) = data.randomSplit(Array(0.75, 0.25), seed = 12345L)

    // Train a NaiveBayes model.
    val nb = new NaiveBayes().setSmoothing(0.00001)        
    val model = nb.fit(trainingData)

    // Select example rows to display.
    val predictions = model.transform(validationData)
    predictions.show()

    // Select (prediction, true label) and compute test error obtain evaluator and compute the classification performnce metrics like accuracy, precision, recall and f1 measure. 
    val evaluator = new BinaryClassificationEvaluator().setLabelCol("label").setMetricName("areaUnderROC")
    val evaluator1 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
    val evaluator2 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("weightedPrecision")
    val evaluator3 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("weightedRecall")
    val evaluator4 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("f1")

    // compute the classification accuracy, precision, recall, f1 measure and error on test data.
    val areaUnderROC = evaluator.evaluate(predictions)
    val accuracy = evaluator1.evaluate(predictions)
    val precision = evaluator2.evaluate(predictions)
    val recall = evaluator3.evaluate(predictions)
    val f1 = evaluator4.evaluate(predictions)
    
    // Print the performance metrics
    println("areaUnderROC = " + areaUnderROC)
    println("Accuracy = " + accuracy)
    println("Precision = " + precision)
    println("Recall = " + recall)
    println("F1 = " + f1)
    println(s"Test Error = ${1 - accuracy}")
    
    data.show(20)

    spark.stop()
  }
}