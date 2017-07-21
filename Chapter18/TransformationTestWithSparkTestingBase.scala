package com.chapter16.SparkTesting

import org.scalatest.Assertions._
import org.apache.spark.rdd.RDD
import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.FunSuite

class TransformationTestWithSparkTestingBase extends FunSuite with SharedSparkContext {
  def tokenize(line: RDD[String]) = {
    line.map(x => x.split(' ')).collect()
  }

  test("works, obviously!") {
    assert(1 == 1)
  }

  test("Words counting") {
    assert(sc.parallelize("Hello world My name is Reza".split("\\W")).map(_ + 1).count == 6)
  }

  test("Testing RDD transformations using a shared Spark Context") {
    val input = List("Testing", "RDD transformations", "using a shared", "Spark Context")
    val expected = Array(Array("Testing"), Array("RDD", "transformations"), Array("using", "a", "shared"), Array("Spark", "Context"))
    val transformed = tokenize(sc.parallelize(input))
    assert(transformed === expected)
  }
}