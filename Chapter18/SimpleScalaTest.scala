package com.chapter16.SparkTesting
import org.scalatest.Assertions._

object SimpleScalaTest {
  def main(args: Array[String]):Unit= {
    val a = 2
    val b = 1
    assert(a == b)
       println("Assertion success")       
  }
}