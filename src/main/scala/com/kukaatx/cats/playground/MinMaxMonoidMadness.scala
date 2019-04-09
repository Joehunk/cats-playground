package com.kukaatx.cats.playground
import cats._
import cats.implicits._
import cats.derived._

object MinMaxMonoidMadness {
  object Max {
  }

  case class Max(value: Option[Long])
  case class Min(value: Option[Long])
  case class Watermarks(min: Min, max: Max)
  case class State(stuff: Map[String, Watermarks])

  implicit val monoidMax: Monoid[Max] = new Monoid[Max] {
    override def empty: Max = Max(None)

    override def combine(x: Max, y: Max): Max = (x, y) match {
      case (Max(Some(xval)), Max(Some(yval))) => Max(Some(Math.min(xval, yval)))
      case (Max(Some(xval)), _) => Max(Some(xval))
      case (_, Max(Some(yval))) => Max(Some(yval))
      case _ => Max(None)
    }
  }

  implicit val monoidMin: Monoid[Min] = new Monoid[Min] {
    override def empty: Min = Min(None)

    override def combine(x: Min, y: Min): Min = (x, y) match {
      case (Min(Some(xval)), Min(Some(yval))) => Min(Some(Math.max(xval, yval)))
      case (Min(Some(xval)), _) => Min(Some(xval))
      case (_, Min(Some(yval))) => Min(Some(yval))
      case _ => Min(None)
    }
  }

  implicit val monoidWatermarks: Monoid[Watermarks] = semi.monoid
  implicit val monoidState: Monoid[State] = semi.monoid

  def updateWatermarks(deviceId: String, value: Long, existing: State): State = {
    existing |+| State(Map(deviceId -> Watermarks(Min(value.some), Max(value.some))))
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