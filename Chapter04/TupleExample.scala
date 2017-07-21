package com.chapter4.CollectionAPI

object TupleExample {
   def main(args: Array[String]) {
      val evenTuple = (2,4,6,8)
      val sumTupleElements =evenTuple._1 + evenTuple._2 + evenTuple._3 + evenTuple._4

      println( "Sum of Tuple Elements: "  + sumTupleElements )
      
     // You can also iterate over the tuple and print it's element using the foreach method
      evenTuple.productIterator.foreach{ evenTuple =>println("Value = " + evenTuple )}

   }
}

