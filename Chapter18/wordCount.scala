package com.chapter16.SparkTesting

import org.apache.spark._
import org.apache.spark.sql.SparkSession

object wordCount {
  val spark = SparkSession
    .builder
    .master("local[*]")
    .config("spark.sql.warehouse.dir", "E:/Exp/")
    .appName("Testing")
    .getOrCreate()
    
  val fileName = "C:/Users/rezkar/Downloads/words.txt";

  def myWordCounter(fileName: String): Long = {
    val input = spark.sparkContext.textFile(fileName)
    val counts = input.flatMap(_.split(" ")).distinct()
    val counter = counts.count()
    counter
  }

  def main(args: Array[String]): Unit = {
    val counter = myWordCounter(fileName)
    println("Number of words: " + counter)
  }
}