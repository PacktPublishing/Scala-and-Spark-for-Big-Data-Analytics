package com.chapter3.OOP

package Country {
   package Professional {
      class Executive {
         private[Professional] var jobTitle = "Big Data Engineer"
         private[Country] var friend = "Saroar Zahan" 
         protected[this] var secrets = "Age"

         def getInfo(another : Executive) {
            println(another.jobTitle)
            println(another.friend)
            println(this.secrets) //ERROR
         }
      }
   }
}