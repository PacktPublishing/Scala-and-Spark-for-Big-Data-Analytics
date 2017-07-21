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



## Filter flights data whose destination is only Miami and show the first 6 entries
showDF(flightDF[flightDF$dest == "MIA", ], numRows = 10)

# Print the schema of this SparkDataFrame
printSchema(flightDF)

# Cache the SparkDataFrame
cache(flightDF)

# Print the first 6 rows of the SparkDataFrame
showDF(flightDF, numRows = 6) ## Or
head(flightDF)

# Show the column names in the SparkDataFrame
columns(flightDF)

# Show the number of rows in the SparkDataFrame
count(flightDF)

# let’s select the all the flights number that are going to IAH that are delayed. Also include the origin airport names
delay_destination_DF <- select(flightDF, "flight", "dep_delay", "origin", "dest")
delay_IAH_DF <- filter(delay_destination_DF, delay_destination_DF$dest == "IAH")
showDF(delay_IAH_DF, numRows = 10)


# Even we can use it to chain data frame operations. At first, group the flights by date and then find the average daily delay. Then finally, write the result into a SparkDataFrame: 
groupBy(flightDF, flightDF$day) %>% summarize(avg(flightDF$dep_delay), avg(flightDF$arr_delay)) -> dailyDelayDF
  
# Print the computed SparkDataFrame
head(dailyDelayDF)


#Some aggregation operation
avg_arr_delay <- collect(select(flightDF, avg(flightDF$arr_delay)))
head(avg_arr_delay)

#Even more complex aggregation
flight_avg_arrival_delay_by_destination <- collect(agg(
  groupBy(flightDF, "dest"), 
  NUM_FLIGHTS=n(flightDF$dest),
  AVG_DELAY = avg(flightDF$arr_delay), 
  MAX_DELAY=max(flightDF$arr_delay),
  MIN_DELAY=min(flightDF$arr_delay)
))

head(flight_avg_arrival_delay_by_destination)

# Use collect to create a local R data frame
local_DF <- collect(flightDF)

# Print the newly created local data frame
head(local_DF)

# Stop the SparkSession now
sparkR.session.stop()
