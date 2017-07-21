package com.chapter14.Serilazition

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.LogManager
import org.apache.log4j.Level
import org.apache.log4j.Logger

object MyLog1 extends Serializable {
 def main(args: Array[String]):Unit= {
   // Stting logger level as WARN
   val log = LogManager.getRootLogger
   log.setLevel(Level.WARN)
   @transient lazy val log2 = org.apache.log4j.LogManager.getLogger("myLogger")

   // Creating Spark Context
   val conf = new SparkConf().setAppName("My App").setMaster("local[*]")
   val sc = new SparkContext(conf)

   //Started the computation and printing the logging inforamtion
   //log.warn("Started")
   //val i = 0
   val data = sc.parallelize(0 to 100000)
   data.foreach(i => log.info("My number"+ i))
   data.collect()
   log.warn("Finished")
 }
}