package com.chapter3.OOP

class Animal {
  var animalName = "notset"
  var animalAge = -1
  def setAnimalName(animalName: String) {
    this.animalName = animalName
  }
  
  def setAnaimalAge(animalAge: Int) {
    this.animalAge = animalAge
  }
  
  def getAnimalName(): String = {
    animalName
  }
  
  def getAnimalAge(): Int = {
    animalAge
  }
}

object RunAnimalExample extends App {
  val animalObj = new Animal
  println(animalObj.getAnimalName)
  println(animalObj.getAnimalAge)
  
  // Now try setting the values of animal name and age as follows:   
  animalObj.setAnimalName("dog")
  animalObj.setAnaimalAge(10)
  println(animalObj.getAnimalName)
  println(animalObj.getAnimalAge)
}
