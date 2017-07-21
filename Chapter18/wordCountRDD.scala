package com.chapter16.SparkTesting
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

class wordCountRDD {
  def prepareWordCountRDD(file: String, spark: SparkSession): RDD[(String, Int)] = {
    val lines = spark.sparkContext.textFile(file)
    lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
  }

}