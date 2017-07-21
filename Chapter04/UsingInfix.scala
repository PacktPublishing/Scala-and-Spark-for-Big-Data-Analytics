package com.chapter4.CollectionAPI
object UsingInfix {
  case class Complex(i: Double, j: Double) {
    def plus(other: Complex): Complex =
      Complex(i + other.i, j + other.j)
  }  
  def main(args: Array[String]): Unit = {    
    val obj = Complex(10, 20)
    val a = Complex(6, 9)
    val b = Complex(3, -6)
    val c = a plus b
    val z = obj.plus(a)
    println(c)
    println(z)
  }
}