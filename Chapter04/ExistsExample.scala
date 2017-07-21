package com.chapter4.CollectionAPI

object ExistsExample {
  def main(args: Array[String]) {
    // Given a list of cities and now check if "Dublin" is included in the list     
    val cityList = List("Dublin", "NY", "Cairo")
    val ifExisitsinList = cityList exists (x => x == "Dublin")
    println(ifExisitsinList)

    // Given a map of countries and their capitals check if Dublin is included in the Map 
    val cityMap = Map("Ireland" -> "Dublin", "UK" -> "London")
    val ifExistsinMap =  cityMap exists (x => x._2 == "Dublin")
    println(ifExistsinMap)
  }
}
