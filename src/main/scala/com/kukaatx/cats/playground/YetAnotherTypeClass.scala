package com.kukaatx.cats.playground

object YetAnotherTypeClass {
  implicit val forString: YetAnotherTypeClass[String] = new YetAnotherTypeClass[String] {
    override def doIt(t: String): Unit = println(s"Did it (String, YetAnotherTypeClass): $t")
  }
}

trait YetAnotherTypeClass[T] {
  def doIt(t: T): Unit
}
