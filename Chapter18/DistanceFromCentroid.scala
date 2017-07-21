package com.chapter16.SparkTesting

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.{Vector, Vectors}

/**
 * Various measures of distances of a set of points from a centroid.
 *
 * @author Abhijit Bose
 * @version 1.0 06/24/2015
 * @since 1.0 06/24/2015
 */

object DistanceFromCentroid {

  /**
   * Calculate sum of distances of a set of points from a centroid. First individual distances
   * between a point and the centroid are calculated and then a global sum is taken. Sum being
   * associative and commutative can be done in parallel over the RDD partitions.
   *
   * @param sc SparkContext
   * @param vPoints a collection of points as Vector
   * @param centroid a centroid point as Vector
   * @return  Accumulated distance in Double
   */
  def calcDistance(sc: SparkContext, vPoints: RDD[Vector], centroid: Vector): Double = {

    // Broadcast centroid to all partitions
    val bcCentroid = sc.broadcast(centroid)

    // For each partition, calculate the sum of distances from centroid to each of the points in
    // that partition. Then, sum up the partial sums from all the partitions.

    val accmDistance = vPoints.mapPartitions{ points => {
      var sum = 0.0
      points.foreach { point => {
        sum += EuclideanVectorSpace.distance(point, bcCentroid.value)
      }}
      Iterator(sum)
    }}.reduce(_ + _)
    accmDistance
  }
}