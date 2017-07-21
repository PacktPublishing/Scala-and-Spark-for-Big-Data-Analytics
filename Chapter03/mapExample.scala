package com.chapter3.ScalaFP
object mapExample {
  implicit class MapReduceTraversable[T, N](val traversable: Traversable[(T, N)]) {
    def reduceByKey(f: (N, N) => N) = traversable.par.groupBy(_._1).mapValues(_.map(_._2)).mapValues(_.reduce(f))
  }
  def main(args: Array[String]) {
    val eventList = List(2, 4, 6, 8, 10) // A simple list      
    println("Original list: " + eventList)
    //Use map    
    val newList1 = eventList.map(x => x * 2)
    println(newList1)
    def func(x: Int) = if (x > 4) Some(x) else None
    val newList2 = eventList.map(x => func(x))
    println(newList2)

    val myList = List(1, 1, 1, 1, 1, 1, 1)
    val reduce = myList.reduce { (x, y) => println(s"$x+$y=${x + y}"); x + y }
    println()
    val parReduce = myList.par.reduce { (x, y) => println(s"$x+$y=${x + y}"); x + y }
    println()

    val fruits = List("apple", "apple", "orange", "apple", "mango", "orange", "apple", "apple", "apple", "apple")
    val reducebyKeyValue = fruits.map(f => (f, 1)).reduceByKey(_ + _)
    println(reducebyKeyValue)
  }
}