package com.kukaatx.cats.playground

object SomeOtherTypeClass {
  implicit val forString: SomeOtherTypeClass[String] = new SomeOtherTypeClass[String] {
    override def doIt(t: String): Unit = println(s"Did it (String, SomeOtherTypeClass): $t")
  }

  implicit val forDouble: SomeOtherTypeClass[Double] = new SomeOtherTypeClass[Double] {
    override def doIt(t: Double): Unit = println(s"Did it (Double, SomeOtherTypeClass): $t")
  }

  def empty[T]: SomeOtherTypeClass[T] = new SomeOtherTypeClass[T] { override def doIt(t: T): Unit = {} }
}

trait SomeOtherTypeClass[T] {
  def doIt(t: T): Unit
}
