package com.chapter16.SparkTesting
import org.scalatest.Assertions._

object CompileOrNot {
  def main(args: Array[String]):Unit= {
    assertDoesNotCompile("val a: String = 1")
    println("assertDoesNotCompile True")
    
    assertTypeError("val a: String = 1")
    println("assertTypeError True")
    
    assertCompiles("val a: Int = 1") 
    println("assertCompiles True")
    
    assertDoesNotCompile("val a: Int = 1")
    println("assertDoesNotCompile True")
  }
}