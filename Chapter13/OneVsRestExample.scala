package com.chapter12.NaiveBayes

import org.apache.spark.ml.classification.{LogisticRegression, OneVsRest}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
import scala.io.Source
import java.io._

/*
 * @author : Md. Rezaul Karim
 */

object OneVsRestExample {
  def main(args: Array[String]): Unit = {
    
    // Create Spark session by specifying master URL, Spark SQL warehouse and applicaiton name as follows: 
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "E:/Exp/")
      .appName(s"OneVsRestExample")
      .getOrCreate()
      
   val path = args(0)

    // load data file and create data frame out of the data and finally show the data frame structure
    //val inputData = spark.read.format("libsvm").load("C:/Users/rezkar/Downloads/spark-2.1.0-bin-hadoop2.7/data/mllib/Letterdata_libsvm.data")
    val inputData = spark.read.format("libsvm").load(path)
    //inputData.show()

    // generate the train/test split.
    val Array(train, test) = inputData.randomSplit(Array(0.7, 0.3))

    // instantiate the base classifier: Logistic regression in this case. 
    val classifier = new LogisticRegression()
      .setMaxIter(500) // Number of maximum iteration (by default more is better)
      .setTol(1E-4) // Tolerance for the stopping criteria (less is better)> Default value is 1E-4
      .setFitIntercept(true) // Specifies if a constant (a.k.a. bias or intercept) should be added to the decision function.
      .setStandardization(true) // Boolean value as true of false
      .setAggregationDepth(50) // More is better
      .setRegParam(0.0001) // Less s better
			.setElasticNetParam(0.01) // Less is better for most of the cases

    // instantiate the One Vs Rest Classifier.
    val ovr = new OneVsRest().setClassifier(classifier)

    // train the multiclass model.
    val ovrModel = ovr.fit(train)

    // score the model on test data.
    val predictions = ovrModel.transform(test)

    // obtain evaluator and compute the classification performnce metrics like accuracy, precision, recall and f1 measure. 
    val evaluator1 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
    val evaluator2 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("weightedPrecision")
    val evaluator3 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("weightedRecall")
    val evaluator4 = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("f1")

    // compute the classification accuracy, precision, recall, f1 measure and error on test data.
    val accuracy = evaluator1.evaluate(predictions)
    val precision = evaluator2.evaluate(predictions)
    val recall = evaluator3.evaluate(predictions)
    val f1 = evaluator4.evaluate(predictions)
    
    
    val writer = new PrintWriter(new File("~/output.txt" ))
    writer.write(s"Accuracy: "+ accuracy + "Precision:  " + precision)
    //writer.write("Prediction Matrix"+ result)
    writer.close()
    
    // Print the performance metrics
    println("Accuracy = " + accuracy);
    println("Precision = " + precision)
    println("Recall = " + recall)
    println("F1 = " + f1)
    println(s"Test Error = ${1 - accuracy}")
    
    //inputData.show()
    predictions.show(false)
    
    // Stop Spark session
    spark.stop()
  }
}

