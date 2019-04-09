package com.kukaatx.cats.playground
import cats.data.{Kleisli, Reader}
import cats.implicits._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object SomeReaderMonadStuff {
  def start(initial: (String, Int)): String = {
    s"initial ${initial._1} - ${initial._2}"
  }

  def doThing(prefix: String)(amount: Int): String = {
    s"$prefix - $amount"
  }

  def doThing2(f: Int => String): Reader[(String, Int), String] = Reader { foo: (String, Int) =>
    f(foo._2)
  }

  def test(initial: String): Unit = {
    val foo = for {
      a <- Reader(doThing(initial))
      b <- Reader(doThing(a))
    } yield {
      b
    }

    println(s"${foo.run(5)}")
  }

  def test2(): Unit = {
    val c = for {
      a <- Reader(start)
      b <- doThing2(doThing(a))
      c <- doThing2(doThing(b))
    } yield {
      c
    }

    println(s"${c.run("foo" -> 5)}")
  }

  case class Request(content: String)

  case class Claims(content: String)

  def handleRequest1(claims: Claims, request: Request): Future[Request] = {
    if (claims.content == "bad") {
      Future.failed(new RuntimeException("Bad."))
    } else {
      Future.successful(Request(request.content + "- good"))
    }
  }

  def handleRequest2(claims: Claims, request: Request): Future[Request] = {
    Future.successful(Request(request.content + "- good2"))
  }

  type RequestHandler[T] = Kleisli[Future, (Claims, T), T]
  def RequestHandler[T]
    : (((Claims, T)) => Future[T]) => Kleisli[Future, (Claims, T), T] = Kleisli[Future, (Claims, T), T]


  def first[T](f: (Claims, T) => Future[T]): RequestHandler[T] = RequestHandler[T](f.tupled)
  def andThen[T](t: T)(f: (Claims, T) => Future[T]): RequestHandler[T] = RequestHandler[T](a => f(a._1, t))

  def andThen2[T](f: (Claims, T) => Future[T], t: T): RequestHandler[T] = RequestHandler[T](a => f(a._1, t))

  def test3(): Unit = {
    val program = for {
      a <- first(handleRequest1)
      b <- andThen(a)(handleRequest2)
    } yield {
      b
    }

    val result1 = Await.result(program.run(Claims("good") -> Request("initial")), 1.second)
    val result2 = Await.result(program.run(Claims("bad") -> Request("initial2"))
      .recover {
        case e: RuntimeException => Request("Caught the error")
      }, 1.second)

    println(s"$result1\n$result2")
  }
}
