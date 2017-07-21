package com.chapter4.CollectionAPI

object ScalaOptions {
  def show(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }

  def main(args: Array[String]) {
    val megacity = Map("Bangladesh" -> "Dhaka", "Japan" -> "Tokyo", "India" -> "Kolkata", "USA" -> "New York")
    println("megacity.get( \"Bangladesh\" ) : " + show(megacity.get("Bangladesh")))
    println("megacity.get( \"India\" ) : " + show(megacity.get("India")))

    // Using getOrElse() method: 
    val message: Option[String] = Some("Hello, world")
    val x: Option[Int] = Some(20)
    val y: Option[Int] = None

    println("message.getOrElse(0): " + message.getOrElse(0))
    println("x.getOrElse(0): " + x.getOrElse(0))
    println("y.getOrElse(10): " + y.getOrElse(10))

    println("message.isEmpty: " + message.isEmpty)
    println("x.isEmpty: " + x.isEmpty)
    println("y.isEmpty: " + y.isEmpty)
  }

}