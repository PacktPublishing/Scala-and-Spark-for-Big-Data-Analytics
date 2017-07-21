install.packages("xml2", dependencies = TRUE)
install.packages("Rcpp", dependencies = TRUE)
install.packages("plyr", dependencies = TRUE)
install.packages("devtools", dependencies = TRUE)
install.packages("MatrixModels", dependencies = TRUE)
install.packages("quantreg", dependencies = TRUE)
install.packages("moments", dependencies = TRUE)
install.packages("ggplot2", dependencies = TRUE)
install.packages("xml2")
install.packages("SparkR")
install.packages(c("digest", "gtable", "scales", "rversions", "lintr"))
install.packages("rJava")

#Configure SparkR
SPARK_HOME = "C:/Users/admin-karim/Downloads/spark-2.1.0-bin-hadoop2.7/R/lib"
HADOOP_HOME= "C:/Users/admin-karim/Downloads/spark-2.1.0-bin-hadoop2.7/bin"
Sys.setenv(SPARK_MEM = "2g")
Sys.setenv(SPARK_HOME = "C:/Users/admin-karim/Downloads/spark-2.1.0-bin-hadoop2.7")
.libPaths(c(file.path(Sys.getenv("SPARK_HOME"), "R", "lib"), .libPaths()))

#Load SparkR
library(SparkR, lib.loc = SPARK_HOME)

# Initialize SparkSession
sparkR.session(appName = "Example", master = "local[2]", spark.sql.warehouse.dir = "C:/Users/admin-karim/Downloads/", sparkConfig = list(spark.driver.memory = "8g"))

#Creating R data frame 
dataPath <- "C:/Exp/nycflights13.csv"
df <- read.csv(file = dataPath, header = T, sep =",")
View(df)

##Converting Spark DataFrame 
flightDF <- as.DataFrame(df)
printSchema(flightDF)
showDF(flightDF, numRows = 10)

# Stop the SparkSession now
sparkR.session.stop()
