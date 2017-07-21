package com.chapter3.OOP

class UsingGenericsForLinkedList[X] {
  private class Node[X](elem: X) {
    var next: Node[X] = _
    override def toString = elem.toString
  }

  private var head: Node[X] = _

  def add(elem: X) {
    val value = new Node(elem)
    value.next = head
    head = value
  }

  private def printNodes(value: Node[X]) {
    if (value != null) {
      println(value)
      printNodes(value.next)
    }
  }

  def printAll() { printNodes(head) }

}

object UsingGenericsForLinkedList {
  def main(args: Array[String]) {
    // To create a list of integers with this class, first create an instance of it, declaring its type as Int:
    val ints = new UsingGenericsForLinkedList[Int]()
    
    // Then populate it with Int values:
    ints.add(1)
    ints.add(2)
    ints.add(3)
    ints.printAll()
    
    // Because the class uses a generic type, you can also create a LinkedList of String:
    val strings = new UsingGenericsForLinkedList[String]()
    strings.add("Salman Khan")
    strings.add("Aamir Khan")
    strings.add("Shah Rukh Khan")
    strings.printAll()
    
    // Or any other type such as Double to use:
    val doubles = new UsingGenericsForLinkedList[Double]()
    doubles.add(10.50)
    doubles.add(25.75)
    doubles.add(12.90)
    doubles.printAll()
  }
}
