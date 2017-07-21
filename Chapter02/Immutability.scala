package com.chapter3.OOP

object Immutability {
  def main (args: Array[String]) {
    var testVar = 10
    //This one will work fine
    testVar = testVar + 10
    var testVal = 6
    //This one will result in an error during compilation
    //Will describe in short that you are trying to reassign to a val
    testVal = testVal + 10
    
    println(testVal)
  }
}