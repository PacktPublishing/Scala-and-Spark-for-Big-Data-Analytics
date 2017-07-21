package com.chapter16.SparkTesting
import org.scalatest.Assertions._
import org.junit.Test
import org.apache.spark.sql.SparkSession

class wordCountTest {
  val spark = SparkSession
    .builder
    .master("local[*]")
    .config("spark.sql.warehouse.dir", "E:/Exp/")
    .appName(s"OneVsRestExample")
    .getOrCreate()
   
    @Test def test() {
      val fileName = "C:/Users/rezkar/Downloads/words.txt"
      val obj = new wordCounterTestDemo()
      assert(obj.myWordCounter(fileName) == 210)
           }
    spark.stop()
}
