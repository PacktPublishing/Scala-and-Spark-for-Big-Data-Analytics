from __future__ import print_function
import os
import sys

from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, IntegerType, StringType
from pyspark.sql.functions import udf
from pyspark.ml.feature import PCA
from pyspark.ml.linalg import Vectors

if __name__ == "__main__":
    spark = SparkSession\
        .builder\
        .appName("PCAExample")\
        .getOrCreate()

    # Creating RDD from the libsvm data file
    myRDD = spark.sparkContext.textFile("C:/Exp/sample_raw_file.txt")
    #myRDD.saveAsTextFile("data/textRDD")

    #Collect the header
    header = myRDD.first()

    # Now filter out the header, make sure the rest looks correct
    textRDD = myRDD.filter(lambda line: line != header)
    newRDD = textRDD.map(lambda k: k.split("\\t"))

    # this creates a dataframe the header.split is providing the names of the columns
    textDF = newRDD.toDF(header.split("\\t"))
    textDF.show()
    textDF.createOrReplaceTempView("transactions")
    spark.sql("SELECT * FROM transactions").show()

    spark.sql("SELECT product_name, price FROM transactions WHERE price >= 500 ").show()
    spark.sql("SELECT * FROM transactions ORDER BY price DESC").show()
    spark.sql("SELECT max(price) AS max_price FROM transactions").show()

spark.stop()

