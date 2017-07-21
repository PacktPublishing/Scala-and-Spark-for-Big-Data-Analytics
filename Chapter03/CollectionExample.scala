package com.chapter3.ScalaFP
import scala.collection._
import scala.collection.mutable.Buffer
import scala.collection.mutable.HashMap

object CollectionExample {
  def main(args: Array[String]) {
    val x = 10
    val y = 15
    val z = 19
    
    Traversable(1, 2, 3)
    Iterable("x", "y", "z")
    Map("x" -> 10, "y" -> 13, "z" -> 17)
    Set("Red", "Green", "Blue")
    SortedSet("Hello,", "world!")
    Buffer(x, y, z)
    IndexedSeq(0.0, 1.0, 2.0)
    LinearSeq(x, y, z)
    List(2, 6, 10)
    HashMap("x" -> 20, "y" -> 19, "z" -> 16)
    
    val list = List(1, 2, 3) map (_ + 1)
    println(list)
    
    val set = Set(1, 2, 3) map (_ * 2)
    println(set)
    
    val list2 = List(x, y, z).map(x => x * 3)
    println(list2)
  }
}