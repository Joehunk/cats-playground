package com.kukaatx.cats.playground
import cats._
import cats.effect.LiftIO

import scala.language.higherKinds

trait AwesomeTrait {
  def getTheThing[F[_]: MonoidK: LiftIO: Monad]: F[String]
}
