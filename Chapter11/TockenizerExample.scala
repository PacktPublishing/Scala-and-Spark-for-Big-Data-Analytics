package com.chapter11.SparkMachineLearning
import org.apache.spark.ml.feature.{ RegexTokenizer, Tokenizer }
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

object TockenizerExample {
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

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val regexTokenizer = new RegexTokenizer()
      .setInputCol("sentence")
      .setOutputCol("words")
      .setPattern("\\W+")
      .setGaps(true)

    val countTokens = udf { (words: Seq[String]) => words.length }

    val tokenized = tokenizer.transform(sentence)
    
    tokenized.select("sentence", "words")
            .withColumn("tokens", countTokens(col("words")))
            .show(false)

    val regexTokenized = regexTokenizer.transform(sentence)
    
    regexTokenized.select("sentence", "words")   
                .withColumn("tokens", countTokens(col("words")))
                .show(false)
  }
}