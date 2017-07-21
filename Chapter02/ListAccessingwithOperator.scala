package com.chapter3.OOP

object ListAccessingwithOperator {
   def main(args: Array[String]) {
      val animal1: List[String] = List("cat", "dog", "rabbit")
      val animal2: List[String] = List("lion", "elephant")
      // concatenate two or more lists with::: operator
      var animal = animal1 ::: animal2
      println( "animal1 ::: animal2 : " + animal )
      // concatenate two lists with Set.:::() method
      animal = animal1.:::(animal2)
      println( "animal1.:::(animal2) : " + animal )
     // concatenate two or more lists by passing them as parameters
      animal = List.concat(animal1, animal1)
      println( "List.concat(animal1, animal2) : " + animal  )
   }
}