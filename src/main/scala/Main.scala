import cats.data.OptionT
import cats.effect.IO
import com.kukaatx.cats.playground.{AwesomeFutureImpl, MinMaxMonoidMadness, SomeReaderMonadStuff, SomeStateMonadStuff, TheMainTypeClass, TraversingFuturesOfThings}
import scala.concurrent.ExecutionContext.Implicits.global

object Main {
  def taglessFinal(): Unit = {
    type Effect[A] = OptionT[IO, A]

    val thing = AwesomeFutureImpl.getTheThing[Effect]

    println(s"It is ${thing.value.unsafeRunSync()}")
  }

  def typeclasses(): Unit = {
    TheMainTypeClass[Int].doIt(1)
    TheMainTypeClass[Double].doIt(1.0)
    TheMainTypeClass[String].doIt("abc")
  }

  def main(argv: Array[String]): Unit = {
    TraversingFuturesOfThings.doSomeTraversing()
  }
}
