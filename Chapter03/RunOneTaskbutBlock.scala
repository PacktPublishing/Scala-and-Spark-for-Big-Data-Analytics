package com.chapter3.ScalaFP
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
object RunOneTaskbutBlock {
  def main(args: Array[String]) {
    // Getting the current time in Milliseconds
    implicit val baseTime = System.currentTimeMillis    
    // Future creation
    val testFuture = Future {
      Thread.sleep(300)
      2 + 2
    }    
    // this is the blocking part
    val finalOutput = Await.result(testFuture, 2 second)
    println(finalOutput)
  }
}