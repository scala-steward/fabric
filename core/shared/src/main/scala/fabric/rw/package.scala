package fabric

import scala.language.experimental.macros

package object rw {
  implicit class Convertible[T](value: T) {
    def json(implicit reader: Reader[T]): Json = reader.read(value)
  }

  implicit class Asable(value: Json) {
    def as[T](implicit writer: Writer[T]): T = writer.write(value)
  }
}