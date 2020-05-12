package example

case class ComputedOutput[T](value: T) {}

package object types {
  type Output[T] = Either[ComputedOutput[T], T]

  type Input[T] = Option[T]

  implicit def stringToInputString(v: String): Input[String] =
    if (v == null) None else Some(v)
}
