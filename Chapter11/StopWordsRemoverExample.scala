package com.chapter11.SparkMachineLearning

import org.apache.spark.ml.feature.{ RegexTokenizer, Tokenizer }
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StopWordsRemover

object StopWordsRemoverExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()

    val sentence = spark.createDataFrame(Seq(
      (0, "Tokenization,is the process of enchanting words,from the raw text"),
      (1, " If you want,to have more advance tokenization,RegexTokenizer,is a good option"),
      (2, " Here,will provide a sample example on how to tockenize sentences"),
      (3, "This way,you can find all matching occurrences"))).toDF("id", "sentence")

    val regexTokenizer = new RegexTokenizer()
      .setInputCol("sentence")
      .setOutputCol("words")
      .setPattern("\\W+")
      .setGaps(true)

    val countTokens = udf { (words: Seq[String]) => words.length }
    val regexTokenized = regexTokenizer.transform(sentence)

    val remover = new StopWordsRemover()
      .setInputCol("words")
      .setOutputCol("filtered")

    val newDF = remover.transform(regexTokenized)
    newDF.select("id", "filtered").show(false)

  }
}