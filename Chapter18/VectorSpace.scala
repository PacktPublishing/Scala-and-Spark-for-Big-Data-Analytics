package com.chapter16.SparkTesting

/**
 * Common algebraic operations in vector space. Define the functions for a class
 * mixed in with this trait that will be appropriate for a specific type of vector space.
 * @example EuclideanVectorSpace which extends this trait.
 *
 * @author Abhijit Bose
 * @version 1.0 06/24/2015
 * @since 1.0 06/24/2015
 */
trait VectorSpace[A] {

  // Distance between two points x and y
  def distance(x: A, y: A): Double

  // Cosine similarity measure between two points x and y
  def cosine(x: A, y: A): Double

  // Centroid of a set of points
  def centroid(points: Seq[A]): A

  // Index and Distance of the point in Array x that is closest to a given point y
  def closest(x: Array[A], y: A): (Int, Double)
}