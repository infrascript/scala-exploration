package example

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
    def get: Nothing = throw new NoSuchElementException("None.get")
  }

  type Input[T] = ProvidedOrComputed[T]

  implicit def stringToInputString(v: String): Input[String] =
    if (v == null) Computed else Provided(v)

  implicit def providedOrComputedToOptionT[T](
      v: ProvidedOrComputed[T]
  ): Option[T] = v match {
    case Computed        => None
    case Provided(value) => Some(value)
  }
}
