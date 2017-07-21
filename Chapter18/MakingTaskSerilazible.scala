package com.chapter16.SparkTesting
import org.apache.spark.sql.SparkSession
import org.apache.log4j.LogManager
import org.apache.log4j.Level
import org.apache.log4j.Logger

class MultiplicaitonOfTwoNumber {
  def multiply(a: Int, b: Int): Int = {
    val product = a * b
    product
  }
}
object MakingTaskSerilazible {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()

    val myRDD = spark.sparkContext.parallelize(0 to 100)
    myRDD.foreachPartition(s => {
      val notSerializable = new MultiplicaitonOfTwoNumber
      println(notSerializable.multiply(s.next(), s.next()))
    })
  }
}
  