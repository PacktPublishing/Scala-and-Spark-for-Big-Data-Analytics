package com.chapter4.CollectionAPI

import ComplexImplicits._
import scala.language.implicitConversions

class Complex(val real: Double, val imaginary: Double) {
  def plus(that: Complex) = new Complex(this.real + that.real, this.imaginary + that.imaginary)
  def plus(n: Double) = new Complex(this.real + n, this.imaginary)
  def minus(that: Complex) = new Complex(this.real - that.real, this.imaginary - that.imaginary)
  def unary(): Double = {
    val value = Math.sqrt(real * real + imaginary * imaginary)
    value
  }
  override def toString = real + " + " + imaginary + "i"
}

object ComplexImplicits {
  implicit def Double2Complex(value: Double) = new Complex(value, 0.0)
  implicit def Tuple2Complex(value: Tuple2[Double, Double]) = new Complex(value._1, value._2)

}

object UsingImplicitConversion {
  def main(args: Array[String]): Unit = {
    val obj = new Complex(5.0, 6.0)
    val x = new Complex(4.0, 3.0)
    val y = new Complex(8.0, -7.0)
    println(x) // prints 4.0 + 3.0i
    println(x plus y) // prints 12.0 + -4.0i
    println(x minus y) // -4.0 + 10.0i
    println(obj.unary) // prints 7.810249675906654

    val z = 4 plus y
    println(z) // prints 12.0 + -7.0i
    val p = (1.0, 1.0) plus z
    println(p) // prints 13.0 + -6.0i
  }
}