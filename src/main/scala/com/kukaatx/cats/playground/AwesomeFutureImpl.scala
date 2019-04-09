package com.kukaatx.cats.playground
import cats._
import cats.implicits._
import cats.effect.{Async, IO, LiftIO}

import scala.concurrent.Future
import scala.language.higherKinds

object AwesomeFutureImpl extends AwesomeTrait {
  private def theActualFutureCall(): Future[Option[String]] = Future.successful(Some("stuff"))

  private def liftFuture[F[_]: LiftIO, A](f: Future[A]): F[A] =
    LiftIO[F].liftIO(IO.fromFuture(IO(f)))

  override def getTheThing[F[_]: MonoidK: LiftIO: Monad]
  : F[String] = {
    liftFuture(theActualFutureCall()).flatMap {
      case Some(s) => Monad[F].pure(s)
      case None => MonoidK[F].empty[String]
    }
  }
}
