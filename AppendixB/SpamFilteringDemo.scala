package com.example.ZeepleinAndSpark

import org.apache.spark.sql.types.{ StructType, StructField, StringType }
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{ SparkSession, Row }
import org.apache.spark.sql.functions.udf
import java.util.regex.Pattern
import org.apache.spark.sql.functions.{ lower, upper }
import org.apache.log4j.{ Level, Logger }
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature._
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.sql.types._

object SpamFilteringDemo {
  val customSchema = StructType(Array(
    StructField("RawLabel", StringType, true),
    StructField("SmsText", StringType, true)))

  def main(args: Array[String]) {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("SpamFilteringDemo")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "C:/Exp/")
      .getOrCreate();

    val smsDataPath = "C:/Exp/smsspamcollection/SMSSpamCollection"
    val data = spark.read.format("com.databricks.spark.csv").option("inferSchema", "false").option("delimiter", "\t").schema(customSchema).load("C:/Exp/smsspamcollection/SMSSpamCollection")
    data.show()

    import spark.implicits._
    import org.apache.spark.sql.functions.count
    import org.apache.spark.sql.functions.col

    // string indexer
    val indexer = new StringIndexer()
      .setInputCol("RawLabel")
      .setOutputCol("label")
    val indexed = indexer.fit(data).transform(data)
    indexed.show()

    val regexTokenizer = new RegexTokenizer()
      .setInputCol("SmsText")
      .setOutputCol("words")
      .setPattern("\\W+")
      .setGaps(true)

    //val countTokens = udf { (words: Seq[String]) => words.length }
    val regexTokenized = regexTokenizer.transform(indexed)

    val remover = new StopWordsRemover()
      .setInputCol("words")
      .setOutputCol("filtered")

    val tokenDF = remover.transform(regexTokenized)
    tokenDF.show()

    val spamTokensWithFrequenciesDF = tokenDF
      .filter($"label" === 1.0)
      .select($"filtered")
      .flatMap(row => row.getAs[Seq[String]](0))
      .filter(word => (word.length() > 1))
      .toDF("Tokens")
      .groupBy($"Tokens")
      .agg(count("*").as("Frequency"))
      .orderBy($"Frequency".desc)

    spamTokensWithFrequenciesDF.createOrReplaceTempView("spamTokensWithFrequenciesDF")
    spamTokensWithFrequenciesDF.show()
    
    val hamTokensWithFrequenciesDF = tokenDF
      .filter($"label" === 0.0)
      .select($"filtered")
      .flatMap(row => row.getAs[Seq[String]](0))
      .filter(word => (word.length() > 1))
      .toDF("Tokens")
      .groupBy($"Tokens")
      .agg(count("*").as("Frequency"))
      .orderBy($"Frequency".desc)      

    hamTokensWithFrequenciesDF.createOrReplaceTempView("hamTokensWithFrequenciesDF")
    //spark.sql("select * from hamTokensWithFrequenciesDF order by Frequency desc limit 20 ").show()
    hamTokensWithFrequenciesDF.show()
    
    
    //For ML
    
    // tf
    val hashingTF = new HashingTF()
      .setInputCol("filtered").setOutputCol("tf")//.setNumFeatures(20)
    val tfdata = hashingTF.transform(tokenDF)

    tfdata.show()

    // idf
    val idf = new IDF().setInputCol("tf").setOutputCol("idf")
    val idfModel = idf.fit(tfdata)
    val idfdata = idfModel.transform(tfdata)
    idfdata.show()

    // assembler
    val assembler = new VectorAssembler()
      .setInputCols(Array("tf", "idf"))
      .setOutputCol("features")
    val assemDF = assembler.transform(idfdata)
    assemDF.show()
    
    //Preparing the DataFrame to traing the ML model
    val mlDF = assemDF.select("label", "features")
    // split
    val Array(trainingData, testData) = mlDF.randomSplit(Array(0.75, 0.25), 12345L)

    // lr
    val lr = new LogisticRegression()
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setRegParam(0.0001)
      .setElasticNetParam(0.0001)
      .setMaxIter(200)

    // build the model
    val lrModel = lr.fit(trainingData)
    
    // predict
    val predict = lrModel.transform(testData)
    predict.show(100)

    // evaluate
    val evaluator = new BinaryClassificationEvaluator()
      .setRawPredictionCol("prediction")

    val accuracy = evaluator.evaluate(predict)
    println("Accuracy = " + accuracy*100 + "%")

    // compute confusion matrix
    val predictionsAndLabels = predict.select("prediction", "label")
      .map(row => (row.getDouble(0), row.getDouble(1)))

    val metrics = new MulticlassMetrics(predictionsAndLabels.rdd)
    println("\nConfusion matrix:")
    println(metrics.confusionMatrix)
  }
}

