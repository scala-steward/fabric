package fabric.io

import fabric.Json

import java.io.File
import java.nio.file.Path
import scala.io.Source

/**
 * Json provides convenience functionality to parse and format JSON to/from fabric Values
 */
object JsonParser extends MultiFormatParser {
  override def parsers: List[FormatParser] = List(
//    HoconParser, JacksonParser, PropertiesParser, XMLParser, YamlParser
    HoconParser, SimpleJsonParser, PropertiesParser, XMLParser, YamlParser
  )

  def apply(file: File, format: Format): Json = apply(Source.fromFile(file, "UTF-8"), format)
  def apply(path: Path, format: Format): Json = apply(Source.fromFile(path.toFile, "UTF-8"), format)

  def apply(file: File): Json = apply(file, Format.Json)
  def apply(path: Path): Json = apply(path, Format.Json)
  def apply(content: String): Json = apply(content, Format.Json)
}