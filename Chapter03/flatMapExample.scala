package com.chapter3.ScalaFP
object flatMapExample {
    def main(args: Array[String]) {
      val eventList = List(2, 4, 6, 8, 10) // A simple list      
      println("Original list: "+ eventList)      
      //Use map    
      def around(x: Int) = List(x-1, x, x+1)
      val newList1 = eventList.map(x=> around(x))
      println("New list from map : " + newList1)      
      //Use flatMap     
      val newList2 = eventList.flatMap(x=> around(x))
      println("New list from flatMap: " + newList2)
    }  
}