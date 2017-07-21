package com.chapter11.SparkMachineLearning

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.evaluation.MulticlassMetrics

object RandomForestDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("PCAExample")//.setMaster("local[*]")
    val sc = new SparkContext(conf)
    val filePath = args(0)

    val data = MLUtils.loadLibSVMFile(sc, filePath)

    val splits = data.randomSplit(Array(0.75, 0.25), seed = 12345L)
    val training = splits(0).cache()
    val test = splits(1)

    // Train a RandomForest mode with an empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = 10
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 50 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 30
    val maxBins = 32

    val model = RandomForest.trainClassifier(training, 
                                             numClasses, 
                                             categoricalFeaturesInfo, 
                                             numTrees, 
                                             featureSubsetStrategy, 
                                             impurity, 
                                             maxDepth, 
                                             maxBins)
    

    // Evaluate model on test instances and compute test error
    val labelAndPreds = test.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    
    val metrics = new MulticlassMetrics(labelAndPreds)

    // Confusion matrix
    println("Confusion matrix:")
    println(metrics.confusionMatrix)

    // Overall Statistics
    val accuracy = metrics.accuracy
    println("Summary Statistics")
    println(s"Accuracy = $accuracy")

    // Precision by label
    val labels = metrics.labels
    labels.foreach { l =>
      println(s"Precision($l) = " + metrics.precision(l))
    }

    // Recall by label
    labels.foreach { l =>
      println(s"Recall($l) = " + metrics.recall(l))
    }

    // False positive rate by label
    labels.foreach { l =>
      println(s"FPR($l) = " + metrics.falsePositiveRate(l))
    }

    // F-measure by label
    labels.foreach { l =>
      println(s"F1-Score($l) = " + metrics.fMeasure(l))
    }

    // Weighted stats
    println(s"Weighted precision: ${metrics.weightedPrecision}")
    println(s"Weighted recall: ${metrics.weightedRecall}")
    println(s"Weighted F1 score: ${metrics.weightedFMeasure}")
    println(s"Weighted false positive rate: ${metrics.weightedFalsePositiveRate}")
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / test.count()
    println("Accuracy = " + (1-testErr) * 100 + " %")
    //println("Learned classification forest model:\n" + model.toDebugString)
  }
}