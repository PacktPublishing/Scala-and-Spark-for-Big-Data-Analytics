package com.chapter16.SparkTesting
import org.apache.spark._
import org.apache.spark.sql.SparkSession

class wordCounterTestDemo {
  val spark = SparkSession
    .builder
    .master("local[*]")
    .config("spark.sql.warehouse.dir", "E:/Exp/")
    .appName(s"OneVsRestExample")
    .getOrCreate()

  def myWordCounter(fileName: String): Long = {
    val input = spark.sparkContext.textFile(fileName)
    val counts = input.flatMap(_.split(" ")).distinct()
    val counter = counts.count()
    counter
  }
}