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


# Using SQL to select columns of data
# First, register the flights SparkDataFrame as a table
createOrReplaceTempView(flightDF, "flight")
destDF <- sql("SELECT dest, origin, carrier FROM flight")
showDF(destDF, numRows=10)

#And then we can use SparkR sql function using condition as follows: 
selected_flight_SQL <- sql("SELECT dest, origin, arr_delay FROM flight WHERE arr_delay >= 120")
showDF(selected_flight_SQL, numRows = 10)

#Bit complex query: Let's find the origins of all the flights that are at least 2 hours delayed where the destiantionn is Iowa. Finally, sort them by arrival delay and limit the count upto 20 and the destinations
selected_flight_SQL_complex <- sql("SELECT origin, dest, arr_delay FROM flight WHERE dest='IAH' AND arr_delay >= 120 ORDER BY arr_delay DESC LIMIT 20")
showDF(selected_flight_SQL_complex)
# Stop the SparkSession now  
sparkR.session.stop()
