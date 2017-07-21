package com.chapter3.ScalaFP
object filterExample {
    def main(args: Array[String]) {
      val range = List.range(1, 10)
      println(range)      
      // Filter out only odd values
      val odds = range.filter( x=> x % 2 != 0)
      println("Odd values: " + odds)      
      // Filter out only even values
      val even = range.filter( x=> x % 2 == 0)
      println("Odd values: " + even)
    }
}