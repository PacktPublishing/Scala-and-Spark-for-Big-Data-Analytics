package com.chapter3.ScalaFP

import java.io.FileReader
import java.io.FileNotFoundException

object UsingTryCatchFinally2 {
  try {
    val f = new FileReader("data/data.txt")
  } catch {
    case ex: FileNotFoundException => println("File not found exception")
  } finally { println("Dude! this code always executes") }
}