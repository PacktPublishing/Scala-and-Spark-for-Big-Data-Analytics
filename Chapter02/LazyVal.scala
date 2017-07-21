package com.chapter3.OOP
import scala.io.Source._

object ReadTextFile extends App {
  lazy val fileContents = fromFile(System.getProperty("user.dir") + "/testFile.txt").getLines
  println(fileContents.toString())
}