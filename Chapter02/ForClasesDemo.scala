package com.chapter3.OOP

class Animal2 {
  var animalName: String = null
  var animalAge: Int = -1

  def setAnimalName(name: String) {
    animalName = name
  }

  def getAnimalName: String = {
    animalName
  }

  def setAnimalAge(age: Int) {
    animalAge = age
  }

  def getAnimalAge: Int = {
    animalAge
  }

  def isAnimalNameSet: Boolean = {
    if (getAnimalName == "notset") false else true
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val obj: Animal2 = new Animal2
    var flag: Boolean = false
    obj.setAnimalName("dog")
    flag = obj.isAnimalNameSet
    println(flag)
    obj.setAnimalName("notset")
    flag = obj.isAnimalNameSet
    println(flag)
  }
}
