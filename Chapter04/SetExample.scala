package com.chapter4.CollectionAPI

object SetExample {
    def main(args: Array[String]) {
    // Empty set of integer type
     var sInteger : Set[Int] = Set()

    // Set of even numbers
    var sEven : Set[Int] = Set(2,4,8,10)
      
   
//Or you can use this syntax
    var sEven2 = Set(2,4,8,10)

val cities = Set("Dublin", "London", "NY")
    val tempNums: Set[Int] = Set()

//Finding Head, Tail, and checking if the sets are empty
println( "Head of cities : " + cities.head )
       println( "Tail of cities : " + cities.tail )
        println( "Check if cities is empty : " + cities.isEmpty )
         println( "Check if tempNums is empty : " + tempNums.isEmpty )

val citiesEurope = Set("Dublin", "London", "NY")
         val citiesTurkey = Set("Istanbul", "Ankara")

         // Sets Concatenation using ++ operator
         var citiesConcatenated = citiesEurope ++ citiesTurkey
         println( "citiesEurope ++ citiesTurkey : " + citiesConcatenated )

         //Also you can use ++ as a method
         citiesConcatenated = citiesEurope.++(citiesTurkey)
         println( "citiesEurope.++(citiesTurkey) : " + citiesConcatenated )

    //Finding minimum and maximum elements in the set
       val evenNumbers = Set(2,4,6,8)

      // Using the min and max methods
      println( "Minimum element in Set(2,4,6,8) : " + evenNumbers.min )
      println( "Maximum element in Set(2,4,6,8) : " + evenNumbers.max )


    }
}
