package com.kukaatx.cats.playground

import cats._
import cats.data.Nested
import cats.implicits._

import scala.async.Async._
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

object TraversingFuturesOfThings {
  def traverseOptions()(implicit ec: ExecutionContext): Future[String] = async {
    val numberMaybe = 1.some

    val seeWhatIDidThere: Option[String] =
      await(numberMaybe.map(_.toString).traverse(returnsAFutureOfThing))

    s"It is: $seeWhatIDidThere"
  }

  def flatTraverseOptions()(implicit ec: ExecutionContext): Future[String] = async {
    val numberMaybe = 4.some

    val seeWhatIDidThere: Option[String] =
      await(numberMaybe.map(_.toString).flatTraverse(returnsAFutureOfOptionOfThing))

    s"It is: $seeWhatIDidThere"
  }

  def flatTraverseListsAndOptions()(implicit ec: ExecutionContext): Future[String] = async {
    val numbers = List(1, 2, 3, 4)

    val nnntz = Nested(returnsAFutureOfOptionOfThing("aaa"))

    val seeWhatIDidThere =
      await(numbers.map(_.toString).map(returnsAFutureOfOptionOfThing).flatTraverse(_.map(_.toList)))

    s"It is: $seeWhatIDidThere"
  }

  def returnsAFutureOfThing(thing: String): Future[String] = Future.successful(s"$thing eventually")

  def returnsAFutureOfOptionOfThing(thing: String)(implicit ec: ExecutionContext): Future[Option[String]] =
    returnsAFutureOfThing(thing).map(_.some)

  def doSomeTraversing()(implicit ec: ExecutionContext): Unit = {
    println(s"${Await.result(traverseOptions(), 10.seconds)}")
    println(s"${Await.result(flatTraverseOptions(), 10.seconds)}")
  }
}
