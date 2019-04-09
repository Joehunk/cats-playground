package com.kukaatx.cats.playground
import cats.data.State
import cats.implicits._

object SomeStateMonadStuff {
  def add(amount: Int)(accum: Int): (Int, Int) = {
    (accum + amount, amount)
  }

  def test(): Unit = {
    val state = for {
      _ <- State(add(2))
      _ <- State(add(4))
    } yield {
      0
    }

    println(s"It is ${state.run(0).value}")
  }
}
