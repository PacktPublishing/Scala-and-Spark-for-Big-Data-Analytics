package com.example.ZeepleinAndSpark

import org.apache.spark.sql.SparkSession
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext

object BankDatawithZepplein {

  case class Bank(age: Integer, job: String, marital: String, education: String, balance: Integer)

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("TrainSplitOCR")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "C:/Exp/").
      getOrCreate();
    val bankText = spark.sparkContext.textFile("data/bank-full.csv")

    // split each line, filter out header (starts with "age"), and map it into Bank case class
    val bank = bankText.map(s => s.split(";")).filter(s => (s.size) > 5).filter(s => s(0) != "\"age\"").map(
      s => Bank(s(0).toInt,
        s(1).replaceAll("\"", ""),
        s(2).replaceAll("\"", ""),
        s(3).replaceAll("\"", ""),
        s(5).replaceAll("\"", "").toInt))

    val sqlContext = new SQLContext(spark.sparkContext)

    import sqlContext.implicits._
    import sqlContext._
    // convert to DataFrame and create temporal table
    val newDF = bank.toDF()
    newDF.show()
    newDF.createOrReplaceTempView("bank")

    spark.sql("select age, count(1) from bank where age <= 50 group by age order by age").show()

    spark.sql("select age, count(1) from bank where age <= 30 group by age order by age").show()
    spark.sql("select max(balance) as MaxBalance from bank where age <= 30 group by age order by MaxBalance DESC").show()

  }
}