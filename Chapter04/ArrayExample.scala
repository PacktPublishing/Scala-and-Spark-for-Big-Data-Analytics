package com.chapter4.CollectionAPI
import Array._

object ArrayExample {
  def main(args: Array[String]) {
    val numbers: Array[Int] = Array[Int](1, 2, 3, 4, 5, 1, 2, 3, 3, 4, 5) // A simple array

    // Print all the element of the array
    println("The full array is: ")
    for (i <- numbers) {
      print(" " + i)
    }

    //Print a particular element for example element 3
    println(numbers(2))

    //Summing all the elements
    var total = 0
    for (i <- 0 to (numbers.length - 1)) {
      total = total + numbers(i)
    }
    println("Sum: = " + total)

    // Finding the smallest element
    var min = numbers(0)
    for (i <- 1 to (numbers.length - 1)) {
      if (numbers(i) < min) min = numbers(i)
    }
    println("Min is: " + min)

    // Finding the largest element
    var max = numbers(0)
    for (i <- 1 to (numbers.length - 1)) {
      if (numbers(i) > max) max = numbers(i)
    }
    println("Max is: " + max)

    //Creating array using range() method
    var myArray1 = range(5, 20, 2)
    var myArray2 = range(5, 20)

    // Print all the array elements
    for (x <- myArray1) {
      print(" " + x)
    }

    println()
    for (x <- myArray2) {
      print(" " + x)
    }

    //Array concatenation
    var myArray3 = concat(myArray1, myArray2)
    // Print all the array elements
    for (x <- myArray3) {
      print(" " + x)
    }

    //Multi-dimensional array
    var myMatrix = ofDim[Int](4, 4)
    // build a matrix
    for (i <- 0 to 3) {
      for (j <- 0 to 3) {
        myMatrix(i)(j) = j
      }
    }
    println();

    // Print two dimensional array
    for (i <- 0 to 3) {
      for (j <- 0 to 3) {
        print(" " + myMatrix(i)(j))
      }
      println()
    }
  }
}
