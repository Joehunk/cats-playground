package com.kukaatx.cats.playground
import cats._
import cats.implicits._
import cats.derived._
import com.twitter.algebird.{Min, Max}

object MinMaxMonoidMadness {
  case class Watermarks(min: Min[Long], max: Max[Long])
  case class State(stuff: Map[String, Watermarks])

  implicit val monoidWatermarks: Monoid[Watermarks] = semi.monoid
  implicit val monoidState: Monoid[State] = semi.monoid

  def updateWatermarks(deviceId: String, value: Long, existing: State): State = {
    existing |+| State(Map(deviceId -> Watermarks(Min(value), Max(value))))
  }

  def test(): Unit = {
    val state = Monoid.empty[State]

    val state1 = updateWatermarks("foo", 10, state)
    val state2 = updateWatermarks("foo", 2, state1)
    val state3 = updateWatermarks("foo", 101, state2)
    val state4 = updateWatermarks("foo", 11, state3)

    println(s"It is $state4")
  }
}