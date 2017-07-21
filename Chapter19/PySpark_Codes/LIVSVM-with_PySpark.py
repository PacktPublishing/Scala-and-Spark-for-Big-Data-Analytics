from __future__ import print_function
import os
import sys

try:
    from pyspark.sql import SparkSession
    from pyspark.mllib.util import MLUtils
    print ("Successfully imported Spark Modules")

except ImportError as e:
    print ("Can not import Spark Modules", e)
    sys.exit(1)

if __name__ == "__main__":
    spark = SparkSession\
        .builder\
        .appName("PCAExample")\
        .getOrCreate()

    # Creating DataFrame from libsvm data file
    myDF = spark.read.format("libsvm").load("C:/Exp/Letterdata_libsvm.data")
    myDF.show()

    # Creating RDD from the libsvm data file
    myRDD = MLUtils.loadLibSVMFile(spark.sparkContext, "C:/Exp/Letterdata_libsvm.data")
    #myRDD.saveAsTextFile("data/myRDD")
    myRDD.first()

spark.stop()

