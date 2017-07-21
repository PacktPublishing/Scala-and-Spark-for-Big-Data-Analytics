package com.chapter3.OOP

object MapTestDemo {
   def main(args: Array[String]) {
      val colorswithHex1 = Map("red" -> "#FF0000", "azure" -> "#F0FFFF", "peru" -> "#CD853F")
      val colorswithHex2 = Map("blue" -> "#0033FF", "yellow" -> "#FFFF00", "red" -> "#FF0000")
      // concatenate two or more Maps with ++ as operator
      var colorswithHex = colorswithHex1 ++ colorswithHex2
      println( "colorswithHex1 ++ colorswithHex2 : " + colorswithHex )
      // concatenate two maps with ++ as method
      colorswithHex = colorswithHex1.++(colorswithHex2)
      println( "colorswithHex1.++(colorswithHex2)) : " + colorswithHex )
   }
}