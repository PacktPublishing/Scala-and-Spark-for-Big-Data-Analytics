package com.chapter14.Serilazition

import org.apache.log4j.LogManager
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession

object myCustomLogwithClosure extends Serializable {
 def main(args: Array[String]): Unit = {   
    val log = LogManager.getRootLogger
    
    //Everything is printed as INFO onece the log level is set to INFO untill you set the level to new level for example WARN. 
    log.setLevel(Level.INFO)
    log.info("Let's get started!")
    
     // Setting logger level as WARN: after that nothing prints other then WARN
    log.setLevel(Level.WARN)
    
    // Creating Spark Session
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName("Logging")
      .getOrCreate()

    // These will note be printed!
    log.info("Get prepared!")
    log.trace("Show if there is any ERROR!")

    //Started the computation and printing the logging information
    log.warn("Started")
    val data = spark.sparkContext.parallelize(0 to 100000)
    data.foreach(i => log.info("My number"+ i))
    data.collect()
    log.warn("Finished")
 }
}


 