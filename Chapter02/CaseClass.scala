package com.chapter3.OOP

object CaseClass {
  def main(args: Array[String]) {
    case class Character(name: String, isHacker: Boolean) // defining a class if a person is a computer hacker
    
    //Nail is a hacker
    val nail = Character("Nail", true)    
  
    //Now let's return a copy of the instance with any requested changes
    val joyce = nail.copy(name = "Joyce")

    // Let's check if both Nail and Joyce are Hackers
    println(nail == joyce)
    
    
    // Let's check if both Nail and Joyce equal
    println(nail.equals(joyce))
    
        
    // Let's check if both Nail and Nail equal
    println(nail.equals(nail))
    
    
   // Let's the hasing code for nail
    println(nail.hashCode())
    
    // Let's the hasing code for nail
    println(nail)

    joyce match {
      case Character(x, true) => s"$x is a hacker"
      case Character(x, false) => s"$x is not a hacker"
    }
  }
}