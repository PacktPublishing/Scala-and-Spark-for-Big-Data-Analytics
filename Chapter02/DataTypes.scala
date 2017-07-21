package com.chapter3.OOP

object DataTypes {
  def main(args: Array[String]) {
    var myVal = 20
    //use println method to print it to the console; you will also notice that if will be inferred as Int
    println(myVal + 10)
    //if you wrote something like below it will throw an error during compilation
    myVal = 40
   // println(myVal * "test")
  }
}