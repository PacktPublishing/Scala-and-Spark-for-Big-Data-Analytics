package com.chapter3.OOP

abstract class MyWriter {
  var message: String = "null"
  def setMessage(message: String):Unit
  def printMessage():Unit
}

class ConsolePrinter extends MyWriter {
  def setMessage(contents: String):Unit= {
    this.message = contents
  }

  def printMessage():Unit= {
    println(message)
  }
}

trait lowerCase extends MyWriter {
  abstract override def setMessage(contents: String) : Unit= 
    printMessage()
}

object AbstractClass {
  def main(args: Array[String]): Unit = {
    val printer:ConsolePrinter = new ConsolePrinter()
    printer.setMessage("Hello! world!")
    printer.printMessage()
  }

}