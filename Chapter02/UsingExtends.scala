package com.chapter3.OOP

class Coordinate(xc: Int, yc: Int) {
  val x: Int = xc
  val y: Int = yc
  
  def move(dx: Int, dy: Int): Coordinate =
    new Coordinate(x + dx, y + dy)
}

class ColorCoordinate(u: Int, v: Int, c: String) extends Coordinate(u, v) {
  val color: String = c
  def compareWith(pt: ColorCoordinate): Boolean =
    (pt.x == x) && (pt.y == y) && (pt.color == color)
    
  override def move(dx: Int, dy: Int): ColorCoordinate =
    new ColorCoordinate(x + dy, y + dy, color)
}


object UsingExtends {
    def main(args: Array[String]): Unit = {
      val obj = new ColorCoordinate(15, 25, "Red")
      println(obj.compareWith(obj))
    }

}
