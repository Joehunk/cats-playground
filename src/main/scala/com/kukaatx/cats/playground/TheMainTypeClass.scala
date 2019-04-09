package com.kukaatx.cats.playground

object TheMainTypeClass {
  implicit def three[T](
      implicit someTypeClass: SomeTypeClass[T] = SomeTypeClass.empty[T],
      someOtherTypeClass: SomeOtherTypeClass[T] = SomeOtherTypeClass.empty[T]
    ): TheMainTypeClass[T] = new TheMainTypeClass[T] {
    override def doIt(t: T): Unit = {
      someTypeClass.doIt(t)
      someOtherTypeClass.doIt(t)
    }
  }

  def apply[T: TheMainTypeClass]: TheMainTypeClass[T] = implicitly[TheMainTypeClass[T]]
}

trait TheMainTypeClass[T] {
  def doIt(t: T): Unit
}
