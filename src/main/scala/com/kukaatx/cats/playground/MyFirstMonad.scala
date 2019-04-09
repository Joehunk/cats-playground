package com.kukaatx.cats.playground
import scala.language.higherKinds

case class MyFirstMonad[F[_], T](value: T => T)

object MyFirstMonad {

}
