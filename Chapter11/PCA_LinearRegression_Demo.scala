package com.chapter11.SparkMachineLearning

import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.feature.PCA
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.regression.LinearRegressionWithSGD

object PCAExample2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()

    val data = MLUtils.loadLibSVMFile(spark.sparkContext, "data/mnist.bz2")
    val df = spark.read.format("libsvm").load("C:/Exp/mnist.bz2")
    df.show(20)
    
    val featureSize = data.first().features.size
    println("Feature Size: " + featureSize)

    val splits = data.randomSplit(Array(0.75, 0.25), seed = 12345L)
    val (training, test) = (splits(0), splits(1))


    val pca = new PCA(featureSize/2).fit(data.map(_.features))
    val training_pca = training.map(p => p.copy(features = pca.transform(p.features)))
    val test_pca = test.map(p => p.copy(features = pca.transform(p.features)))

    val numIterations = 20
    val stepSize = 0.0001
    val model = LinearRegressionWithSGD.train(training, numIterations, stepSize)
    val model_pca = LinearRegressionWithSGD.train(training_pca, numIterations, stepSize)

    val valuesAndPreds = test.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }

    val valuesAndPreds_pca = test_pca.map { point =>
      val score = model_pca.predict(point.features)
      (score, point.label)
    }

    val MSE = valuesAndPreds.map { case (v, p) => math.pow(v - p, 2) }.mean()
    val MSE_pca = valuesAndPreds_pca.map { case (v, p) => math.pow(v - p, 2) }.mean()

    println("Mean Squared Error = " + MSE)
    println("PCA Mean Squared Error = " + MSE_pca)  
    
    println("Model coefficients:"+ model.toString())
    println("Model with PCA coefficients:"+ model_pca.toString())
    

    spark.stop()

  }
}
