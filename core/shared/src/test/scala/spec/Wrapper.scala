package spec

import fabric.rw._

case class Wrapper[T](name: String, value: T, other: Option[T])

object Wrapper {
  implicit def rw[T: RW]: RW[Wrapper[T]] = ccRW
}