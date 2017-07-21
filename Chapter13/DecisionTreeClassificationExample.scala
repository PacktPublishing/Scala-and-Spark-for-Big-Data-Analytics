package com.chapter12.NaiveBayes

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.sql.SparkSession

/*
 * @author : Md. Rezaul Karim
 */

object DecisionTreeClassificationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()
      
    // Load the data stored in LIBSVM format as a DataFrame.
    val data = spark.read.format("libsvm").load("C:/Users/rezkar/Downloads/spark-2.1.0-bin-hadoop2.7/data/mllib/Letterdata_libsvm.data")

    // Index labels, adding metadata to the label column.
    // Fit on whole dataset to include all labels in index.
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)
      
    // Automatically identify categorical features, and index them.
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4) // features with > 4 distinct values are treated as continuous.
      .fit(data)

    // Split the data into training and test sets (25% held out for testing).
    val Array(trainingData, testData) = data.randomSplit(Array(0.75, 0.25), 12345L)

    // Train a DecisionTree model.
    val dt = new DecisionTreeClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")

    // Convert indexed labels back to original labels.
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)

    // Chain indexers and tree in a Pipeline.
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

    // Train model. This also runs the indexers.
    val model = pipeline.fit(trainingData)

    // Make predictions.
    val predictions = model.transform(testData)

    // Select example rows to display.
    predictions.select("predictedLabel", "label", "features").show(5)
    
    // obtain evaluator and compute the classification performnce metrics like accuracy, precision, recall and f1 measure. 
    val evaluator1 = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
    val evaluator2 = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("weightedPrecision")
    val evaluator3 = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("weightedRecall")
    val evaluator4 = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("f1")

    // compute the classification accuracy, precision, recall, f1 measure and error on test data.
    val accuracy = evaluator1.evaluate(predictions)
    val precision = evaluator2.evaluate(predictions)
    val recall = evaluator3.evaluate(predictions)
    val f1 = evaluator4.evaluate(predictions)
    
    // Print the performance metrics
    println("Accuracy = " + accuracy);
    println("Precision = " + precision)
    println("Recall = " + recall)
    println("F1 = " + f1)
    println(s"Test Error = ${1 - accuracy}")

    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
    println("Learned classification tree model:\n" + treeModel.toDebugString)
    
    predictions.show();

    spark.stop()
  }
}