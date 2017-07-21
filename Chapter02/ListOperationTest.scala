package com.chapter3.OOP

object ListOperationTest {
   def main(args: Array[String]) {
      val animal1 = Set("cat", "dog", "rabbit")
      val animal2 = Set("lion", "elephant")
      // concatenate two or more sets with ++ as operator
      var animal = animal1 ++ animal2
      println( "animal1 ++ animal2 : " + animal )
      // concatenate two sets with ++ as method
      animal = animal1.++(animal2)
      println( "animal1.++(animal2) : " + animal )
   }
}