import os
import sys

try:
    from pyspark.sql import SparkSession
    from pyspark.sql import SparkSession
    from pyspark.ml.feature import PCA
    from pyspark.ml.linalg import Vectors
    print("Successfully imported Spark Modules")

except ImportError as e:
    print("Can not import Spark Modules", e)
    sys.exit(1)

spark = SparkSession \
        .builder \
        .appName("PCAExample") \
        .getOrCreate()

data = [(Vectors.sparse(5, [(1, 1.0), (3, 7.0)]),),
            (Vectors.dense([2.0, 0.0, 3.0, 4.0, 5.0]),),
            (Vectors.dense([4.0, 0.0, 0.0, 6.0, 7.0]),)]
df = spark.createDataFrame(data, ["features"])

pca = PCA(k=3, inputCol="features", outputCol="pcaFeatures")
model = pca.fit(df)

result = model.transform(df).select("pcaFeatures")
result.show(truncate=False)

spark.stop()

