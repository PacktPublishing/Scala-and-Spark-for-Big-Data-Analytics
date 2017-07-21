package com.chapter3.ScalaFP

object ListScala {
    def main(args: Array[String]) {
      val eventList = List(2, 4, 6, 8, 10) // A simple list      
      val mappedList = eventList.map(x => x*2) // Mapped each value by multiplying them by 2
      println("Original list: "+ eventList)
      println("Mapped list: "+ mappedList)
      
      //Use map to return a list from function      
      def func(x: Int) = if (x > 4) Some(x) else None
      val newList = eventList.map(x=> func(x))
      println("New list: " + newList)
    }  
}