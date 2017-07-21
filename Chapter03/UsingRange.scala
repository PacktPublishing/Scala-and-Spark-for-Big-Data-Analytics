package com.chapter3.ScalaFP

object UsingRangeWithForLoop {
   def main(args: Array[String]):Unit= {
      var i = 0;      
      // for loop execution with a range
      for( i <- 1 to 20){
         println( "Value of i: " + i )
      }
   }
}
