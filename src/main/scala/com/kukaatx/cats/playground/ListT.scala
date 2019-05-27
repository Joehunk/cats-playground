package com.kukaatx.cats.playground
import cats._
import cats.implicits._

import scala.annotation.tailrec
import scala.language.higherKinds

case class ListT[F[_], A](value: F[List[A]])

object ListT {
  // The "?" notation below requires the "kind projector" compiler plugin. See build.sbt
  implicit def listTransformerMonad[F[_]](implicit F: Monad[F]) = new Monad[ListT[F, ?]] {
    override def pure[A](x: A): ListT[F, A] = ListT(F.pure(List(x)))

    override def flatMap[A, B](fa: ListT[F, A])(f: A => ListT[F, B]): ListT[F, B] = {
      ListT {
        fa.value.flatMap { listA =>
          val pures = listA.map(F.pure)

          pures.flatTraverse(_.map(f).flatMap(_.value))
        }
      }
    }

    private def doRecursionNotStackSafe[A, B](list: ListT[F, Either[A, B]])(f: A => ListT[F, Either[A, B]]): ListT[F, B] = {
      ListT {
        list.value.flatMap { listOfAOrB =>
          val listOfListTOfB = listOfAOrB.map {
            case Left(a) => doRecursionNotStackSafe(f(a))(f)
            case Right(b) => ListT(F.pure(List(b)))
          }

          listOfListTOfB.flatTraverse(_.value)
        }
      }
    }

    override def tailRecM[A, B](a: A)(f: A => ListT[F, Either[A, B]]): ListT[F, B] = {
      val element = ListT(F.pure(List(Either.left[A, B](a))))

      doRecursionNotStackSafe(element)(f)
    }
  }
}
