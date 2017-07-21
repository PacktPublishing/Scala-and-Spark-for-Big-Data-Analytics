package com.chapter3.OOP

class OuterClass {
   class InnerClass {
      def printName() { println("Hello world, my name is Asif Karim!") }
      
      class InnerMost {
         printName() // OK
      }
   }
   (new InnerClass).printName() // OK because now printName() is public
}


package MyPackage {
   class SuperClass {
      private def printName() { println("Hello world, my name is Asif Karim!") }
   }
   
   class SubClass extends SuperClass {
      //printName() //ERROR
   }
   
   class SubsubClass {
      //(new SuperClass).printName() // Error: printName is not accessible
   }
}