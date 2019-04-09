package com.kukaatx.cats.playground

object SomeTypeClass {
  implicit val forInt: SomeTypeClass[Int] = new SomeTypeClass[Int] {
    override def doIt(t: Int): Unit = println(s"Did it (Int, SomeTypeClass): $t")
  }

  implicit val forString: SomeTypeClass[String] = new SomeTypeClass[String] {
    override def doIt(t: String): Unit = println(s"Did it (String, SomeTypeClass): $t")
  }

  def empty[T]: SomeTypeClass[T] = new SomeTypeClass[T] { override def doIt(t: T): Unit = {} }
}

trait SomeTypeClass[T] {
  def doIt(t: T): Unit
}
