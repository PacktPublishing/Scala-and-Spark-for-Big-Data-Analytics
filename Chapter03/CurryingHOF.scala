package com.chapter3.ScalaFP

// Here's a trait encapsulating the definition your coworker sent.
trait Curry {
  def curry[A, B, C](f: (A, B) => C): A => B => C
  def uncurry[A, B, C](f: A => B => C): (A, B) => C
}

object CurryImplement extends Curry {
  // I'm going to implement uncurry first because it's the easier of the
  // two to understand.  The bit in curly braces after the equal sign is a
  // function literal which takes two arguments and applies the to (i.e.
  // uses it as the arguments for) a function which returns a function.
  // It then passes the second argument to the returned function.
  // Finally it returns the value of the second function.
  def uncurry[X, Y, Z](f: X => Y => Z): (X, Y) => Z = { (a: X, b: Y) => f(a)(b) }

  // The bit in curly braces after the equal sign is a function literal
  // which takes one argument and returns a new function.  I.e., curry()
  // returns a function which when called returns another function
  def curry[X, Y, Z](f: (X, Y) => Z): X => Y => Z = { (a: X) => { (b: Y) => f(a, b) } }
}

object RefactoringHigherOrderFunction {
  def main(args: Array[String]): Unit = {
    def add(x: Int, y: Long): Double = x.toDouble + y
    val addSpicy = CurryImplement.curry(add)
    println(addSpicy(3)(1L)) // prints "4.0"
    
    val increment = addSpicy(2) // increment holds a function which takes a long and adds 1 to it.
    println(increment(1L)) // prints "3.0"
    
    val unspicedAdd = CurryImplement.uncurry(addSpicy)
    println(unspicedAdd(1, 6L)) // prints "7.0"
  }

}