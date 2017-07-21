package com.chapter11.SparkMachineLearning

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{ OneHotEncoder, StringIndexer }
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions.year
import org.apache.spark.ml.{ Pipeline, PipelineStage }
import org.apache.spark.ml.classification.{ LogisticRegression, LogisticRegressionModel }
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.{ DataFrame, SparkSession }
import scala.collection.mutable
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

object OneHotEncoderDemo2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()

    val df = spark.createDataFrame(
      Seq((0, "Jason", "Germany"),
        (1, "David", "France"),
        (2, "Martin", "Spain"),
        (3, "Jason", "USA"),
        (4, "Daiel", "UK"),
        (5, "Moahmed", "Bangladesh"),
        (6, "David", "Ireland"),
        (7, "Jason", "Netherlands"))).toDF("id", "name", "address")

    df.show(false)

    val indexer = new StringIndexer()
      .setInputCol("name")
      .setOutputCol("categoryIndex")
      .fit(df)
    val indexed = indexer.transform(df)

    val encoder = new OneHotEncoder()
      .setInputCol("categoryIndex")
      .setOutputCol("categoryVec")

    val encoded = encoder.transform(indexed)
    encoded.show()
    
    spark.stop()
  }
}

