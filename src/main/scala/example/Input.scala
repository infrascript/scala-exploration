package example

import io.circe.{Encoder, Json}
import scala.language.implicitConversions

case class ComputedOutput[T](value: T) {}

package object types {
  type Output[T] = Either[ComputedOutput[T], T]

  sealed abstract class ProvidedOrComputed[+A] {
    def get: A
  }

  final case class Provided[+A](value: A) extends ProvidedOrComputed[A] {
    def get: A = value
  }

  case object Computed extends ProvidedOrComputed[Nothing] {
    def get: Nothing = throw new NoSuchElementException("Computed.get")
  }

  implicit final def encodeProvidedOrComputed[A](implicit e: Encoder[A]): Encoder[ProvidedOrComputed[A]] = {
    case Provided(v) => e(v)
    case Computed    => Json.Null
  }

  implicit final def encodeProvided[A](implicit e: Encoder[A]): Encoder[Provided[A]] = e.contramap(_.get)

  implicit final val encodeComputed: Encoder[Computed.type] = (_: Computed.type) => Json.Null

  type Input[T] = ProvidedOrComputed[T]

  type StringRefInput = Input[() => String]
  implicit def toStringRefInput(v: => String): StringRefInput = Provided(() => v)

  implicit def stringToInputString(v: String): Input[String] = if (v == null) Computed else Provided(v)

  implicit def providedOrComputedToOptionT[T](v: ProvidedOrComputed[T]): Option[T] =
    v match {
      case Computed        => None
      case Provided(value) => Some(value)
    }
}
