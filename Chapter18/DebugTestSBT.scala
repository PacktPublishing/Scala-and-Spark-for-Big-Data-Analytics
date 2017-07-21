package com.chapter16.SparkTesting

import org.apache.spark.sql.SparkSession

object DebugTestSBT {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "C:/Exp/")
      .appName("Logging")
      .getOrCreate()
      
    spark.sparkContext.setCheckpointDir("C:/Exp/")

    println("-------------Attach debugger now!--------------")
    Thread.sleep(8000)

    // Your job code here, with breakpoints set on the lines you want to pause
  }
}
