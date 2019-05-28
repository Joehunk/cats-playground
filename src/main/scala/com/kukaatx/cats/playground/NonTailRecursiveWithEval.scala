package com.kukaatx.cats.playground

import cats.Eval

object NonTailRecursiveWithEval {
  def superInefficientCountEvensStackUnsafe(n: Long): Long = n match {
    case _ if n <= 0 => 0
    case _ if n % 2L == 1L => superInefficientCountEvensStackUnsafe(n-1)
      // Not tail rec...it has a + in the tail position
    case _  => superInefficientCountEvensStackUnsafe(n-1) + 1
  }

  def superInefficientCountEvensWithEval(n: Long): Eval[Long] = Eval.now(n).flatMap {
    case n if n <= 0 => Eval.now(0)
    case n if n % 2L == 1L => superInefficientCountEvensWithEval(n-1)
    case n => superInefficientCountEvensWithEval(n-1).map(_ + 1)
  }

  def superInefficientCountEvensStackSafe(n: Long): Long = superInefficientCountEvensWithEval(n).value
}
