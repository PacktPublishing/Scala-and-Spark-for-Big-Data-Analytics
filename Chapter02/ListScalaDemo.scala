package com.chapter3.OOP

object ListScalaDemo {
  def main(args: Array[String]) {
    //List of strings
    val animal: List[String] = List("cat", "dog", "rabbit")
    
    // List of some integers
    val number_list: List[Int] = List(2, 4, 6, 8)
    
    // Empty List
    val empty: List[Nothing] = List()
    
    // List with two dimensions
    val numbers2D: List[List[Int]] =
      List(
        List(2, 4, 6),
        List(8, 10, 12),
        List(14, 16, 18))
        
    val numbers = Nil
    println("Complete integers : " + number_list)
    println("Head of integers : " + number_list.head)
    println("Tail of integers : " + number_list.tail)    
            
    println("Complete animals : " + animal)
    println("Head of animal : " + animal.head)
    println("Tail of animal : " + animal.tail)
    
    println("Check if animal is empty : " + animal.isEmpty)
    println("Check if numbers is empty : " + numbers.isEmpty)
  }
}