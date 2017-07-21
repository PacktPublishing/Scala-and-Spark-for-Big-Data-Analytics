library(SparkR)

# Initialize SparkSession
sparkR.session(appName = "SparkR-ML-mlp-example")

# $example on$
# Load training data
df <- read.df("C:/Exp/Letterdata_libsvm.data", source = "libsvm")
training <- df
test <- df

# specify layers for the neural network:
# input layer of size 4 (features), two intermediate of size 5 and 4
# and output of size 3 (classes)
layers = c(17, 8, 4, 26)

# Fit a multi-layer perceptron neural network model with spark.mlp
model <- spark.mlp(training, label ~ features, maxIter = 100,
                   layers = layers, blockSize = 128, seed = 1234)

# Model summary
summary(model)

# Prediction
predictions <- predict(model, test)
showDF(predictions)
# $example off$
