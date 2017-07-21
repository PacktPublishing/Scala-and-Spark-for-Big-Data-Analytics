package com.chapter16.SparkTesting
import org.scalatest.Assertions._

object AssertResult {
    def main(args: Array[String]):Unit= {
       val x = 10
       val y = 6
       assertResult(3) {
             x - y
                }
     }
}