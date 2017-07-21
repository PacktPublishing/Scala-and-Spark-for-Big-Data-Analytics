package com.chapter4.CollectionAPI

object ListExample {
    def main(args: Array[String]) {
        // List of cities
        val cities = "Dublin" :: "London" :: "NY" :: Nil

        // List of Even Numbers
        val nums = 2 :: 4 :: 6 :: 8 :: Nil

        // Empty List.
        val empty = Nil

        // Two dimensional list
        val dim = 1 :: 2 :: 3 :: Nil ::
                        4 :: 5 :: 6 :: Nil ::
                        7 :: 8 :: 9 :: Nil :: Nil
        val temp = Nil
            
             // Getting the first element in the list
             println( "Head of cities : " + cities.head )

             // Getting all the elements but the last one
        println( "Tail of cities : " + cities.tail )

             //Checking if cities/temp list is empty
        println( "Check if cities is empty : " + cities.isEmpty )
        println( "Check if temp is empty : " + temp.isEmpty )
     
       val citiesEurope = "Dublin" :: "London" :: "Berlin" :: Nil
       val citiesTurkey = "Istanbul" :: "Ankara" :: Nil

       //Concatenate two or more lists with :::
       var citiesConcatenated = citiesEurope ::: citiesTurkey
       println( "citiesEurope ::: citiesTurkey : "+citiesConcatenated )
      
       // using the concat method
       citiesConcatenated = List.concat(citiesEurope, citiesTurkey)
       println( "List.concat(citiesEurope, citiesTurkey) : " + citiesConcatenated  )

    }
}

