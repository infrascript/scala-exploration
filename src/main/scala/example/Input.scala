package example

import io.circe.{Encoder, Json}
import scala.language.implicitConversions

package object types {

  sealed abstract class Input[+A] {
    def get: A
  }

  final case class Provided[+A](value: A) extends Input[A] {
    def get: A = value
  }

  case object Computed extends Input[Nothing] {
    def get: Nothing = throw new NoSuchElementException("Computed.get")
  }

  implicit final def encodeInput[A](implicit e: Encoder[A]): Encoder[Input[A]] = {
    case Provided(v) => e(v)
    case Computed    => Json.Null
  }

  implicit final def encodeProvided[A](implicit e: Encoder[A]): Encoder[Provided[A]] = e.contramap(_.get)

  implicit final val encodeComputed: Encoder[Computed.type] = (_: Computed.type) => Json.Null

  type StringRefInput = Input[() => String]
  implicit def toStringRefInput(v: => String): StringRefInput = Provided(() => v)

  implicit def stringToInputString(v: String): Input[String] = if (v == null) Computed else Provided(v)

  implicit def providedOrComputedToOptionT[T](v: Input[T]): Option[T] =
    v match {
      case Computed        => None
      case Provided(value) => Some(value)
    }
}
