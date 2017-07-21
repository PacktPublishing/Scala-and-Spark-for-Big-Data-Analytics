#Configure SparkR
#Configure SparkR
SPARK_HOME = "C:/Users/admin-karim/Downloads/spark-2.1.0-bin-hadoop2.7/R/lib"
HADOOP_HOME= "C:/Users/admin-karim/Downloads/spark-2.1.0-bin-hadoop2.7/bin"
Sys.setenv(SPARK_MEM = "2g")
Sys.setenv(SPARK_HOME = "C:/Users/admin-karim/Downloads/spark-2.1.0-bin-hadoop2.7")
.libPaths(c(file.path(Sys.getenv("SPARK_HOME"), "R", "lib"), .libPaths()))

#Load SparkR
library(SparkR, lib.loc = SPARK_HOME)

# Initialize SparkSession
sparkR.session(appName = "Example", master = "local[*]", sparkConfig = list(spark.driver.memory = "8g"))

# Point the data file path: 
dataPath <- "C:/Exp/nycflights13.csv"

#Creating DataFrame using external data source API
flightDF <- read.df(dataPath, 
                        header='true', 
                        source = "com.databricks.spark.csv", 
                        inferSchema='true')
printSchema(flightDF)
showDF(flightDF, numRows = 10)

# Stop the SparkSession now
sparkR.session.stop()
