package com.chapter3.ScalaFP

import java.io.BufferedReader
import java.io.IOException
import java.io.FileReader

object ScalaExceptionHandling {
  def errorHandler(e:IOException){
    println("stop doing somehting!")
  }
  val file:String = "C:/Exp/input.txt"
  val input = new BufferedReader(new FileReader(file))
try {
  try {
    for (line <- Iterator.continually(input.readLine()).takeWhile(_ != null)) {
      Console.println(line)
    }
  } finally {
    input.close()
  }
} catch {
  case e:IOException => errorHandler(e)
}

  
}

