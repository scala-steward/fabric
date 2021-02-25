package hierarchical

import hierarchical.rw._

import scala.language.implicitConversions

object Test {
  def main(args: Array[String]): Unit = {
//    val v: Obj = obj(
//      "name" -> "Matt \"Matteo\" Hicks",
//      "age" -> 41,
//      "numbers" -> List(1, 2, 3),
//      "address" -> obj(
//        "street" -> "123 Somewhere Rd.\nBox 123",
//        "city" -> "San Jose",
//        "state" -> "California",
//        "zipcode" -> 95136
//      )
//    )
//    println(v)
//    val state = v("address" \ "state")
//    println(s"State: $state")
//    val updated = v.modify("address" \ "state") { value =>
//      println(s"Updating: $value")
//      str("Tennessee")
//    }
//    println(s"Updated: $updated")
//    println(s"Original: $v")
//    val removed = v.remove("address" \ "state")
//    println(s"Removed: $removed")

    // TODO: merge

    val person = Person("Matt Hicks", 41)
    implicit val personRW: ReadableWritable[Person] = new ClassRW[Person] {
      override protected def t2Map(t: Person): Map[String, Value] = Map(
        "name" -> str(t.name),
        "age" -> num(t.age.toDouble)
      )

      override protected def map2T(map: Map[String, Value]): Person = Person(
        name = map("name").asStr.value,
        age = map("age").asNum.value.toInt
      )
    }

//    val value = person.as[Value]
//    val back = value.as[Person]
  }

  case class Person(name: String, age: Int)
}