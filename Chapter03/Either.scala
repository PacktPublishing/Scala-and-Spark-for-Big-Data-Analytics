package com.chapter3.ScalaFP

import java.net.URL

import scala.io.Source

object Either {
  def getData(dataURL: URL): Either[String, Source] =
    if (dataURL.getHost.contains("xxx"))
      Left("Requested URL is blocked or prohibited!")
    else
      Right(Source.fromURL(dataURL))
      
  def main(args: Array[String]) {
      val either1 = getData(new URL("http://www.xxx.com"))    
      println(either1)
      
      val either2 = getData(new URL("http://www.google.com"))    
      println(either2)
  }
}