package com.chapter3.OOP

object PatternMatchingDemo2 {
  def main(args: Array[String]): Unit = {
    println(comparison("two"))
    println(comparison("test"))
    println(comparison(1))
  }
  def comparison(x: Any): Any = x match {
    case 1 => "one"
    case "five" => 5
    case _ => "nothing else"
  }
}
