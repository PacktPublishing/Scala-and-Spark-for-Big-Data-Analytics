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

#Giving ggplot2 a try
install.packages("ggplot2")
library(ggplot2)

#What if we directly try to use our SparkSQL DataFrame class into a ggplot?
my_plot <- ggplot(data=flightDF, aes(x=factor(carrier)))

# ERROR: ggplot2 doesn't know how to deal with data of class SparkDataFrame. 
#Obviously it doesn't work that way. The ggplot function doesn't know how to deal with that type of distributed data frames (the Spark ones). Instead, we need to collect the data locally as follows.
flight_local_df <- collect(select(flightDF,"carrier"))

#Let's have a look at what we got.
str(flight_local_df)


#That is, when we collect results from a SparkSQL DataFrame we get a regular R data.frame. Very convenient since we can manipulate it as we need to. 
#And now we are ready to create the ggplot object as follows.
my_plot <- ggplot(data=flight_local_df, aes(x=factor(carrier), fill = carrier))

#And now we can give the plot a proper representation (e.g. a bar plot).
my_plot + geom_bar() + xlab("Carrier")

carrierDF = sql("SELECT carrier, COUNT(*) as cnt FROM flight GROUp BY carrier ORDER BY cnt DESC")
showDF(carrierDF)
