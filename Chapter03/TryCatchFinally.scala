package com.chapter3.ScalaFP
import java.io.IOException
import java.io.FileReader
import java.io.FileNotFoundException
object TryCatch {
  def main(args: Array[String]) {
    try {
      val f = new FileReader("data/data.txt")
    } catch {
      case ex: FileNotFoundException => println("File not found exception")
      case ex: IOException => println("IO Exception") 
    } finally {
      println("Finally block always executes");
    }
  }
}