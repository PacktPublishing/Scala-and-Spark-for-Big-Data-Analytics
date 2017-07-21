package com.chapter3.OOP

object ListOperationMaxMinTest {
   def main(args: Array[String]) {
      val numbers = Set(1,2,5,0,10,4)
      // find min and max of the elements
      println( "The Minimum of the set Set(1,2,5,0,10,4) : " + numbers.min )
      println( "The Maximum of the set Set(1,2,5,0,10,4): " + numbers.max )
   }
}