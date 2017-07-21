import os
import sys

try:
    from pyspark.sql import SparkSession
    print("Successfully imported Spark Modules")

except ImportError as e:
    print("Can not import Spark Modules", e)
    sys.exit(1)

if __name__ == "__main__":
    spark = SparkSession\
        .builder\
        .appName("PCAExample")\
        .getOrCreate()

    df = spark.read.format("com.databricks.spark.csv").option("header", "true").load("C:/Exp/nycflights13.csv")
    df.printSchema()
    df.show()

spark.stop()




