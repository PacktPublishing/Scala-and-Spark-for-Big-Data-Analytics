package com.chapter3.OOP

import java.io._

class cPoint(val xc: Int, val yc: Int) {
   var x: Int = xc
   var y: Int = yc
   
   def oMove(dx: Int, dy: Int) {
      x = x + dx
      y = y + dy
      println ("Lcation X: " + x);
      println ("Location Y: " + y);
   }
}

object Dimension {
   def main(args: Array[String]) {
     // Let's call the method cPoint wth some real value -say 15, 27
      val pt = new cPoint(15, 27);

      // Now let's move to a new location with coordinate 20, 30
      pt.oMove(20, 30);
      
      // Now let's move to a new location with coordinate 25, 45
      pt.oMove(25, 45);
   }
}