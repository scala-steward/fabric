package fabric.parse

import fabric._

case class JsonWriter(config: JsonWriterConfig) { w =>
  def apply(value: Value): String = {
    write(value, 0)
  }

  private def write(value: Value, depth: Int): String = value match {
    case Arr(v) =>
      val content = v.map { value =>
        s"${config.newLine()}${config.indent(depth + 1)}${write(value, depth + 1)}"
      }.mkString(",")
      s"[$content${config.newLine()}${config.indent(depth)}]"
    case Bool(b) => b.toString
    case Null => "null"
    case NumInt(n) => n.toString
    case NumDec(n) => n.toString()
    case Obj(map) =>
      val content = map.toList.map {
        case (key, value) =>
          s"${config.newLine()}${config.indent(depth + 1)}${config.encodeString(key)}${config.keyValueSeparator()}${write(value, depth + 1)}"
      }.mkString(",")
      s"{$content${config.newLine()}${config.indent(depth)}}"
    case Str(s) => config.encodeString(s)
  }
}

object JsonWriter {
  lazy val Default: JsonWriter = JsonWriter(JsonWriterConfig.Standard())
  lazy val Compact: JsonWriter = JsonWriter(JsonWriterConfig.Compact)
}