/*
 * Copyright (c) 2021 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fabric.search

import fabric._
import fabric.define.DefType
import fabric.rw._

import scala.util.Try

case class ByOffset(offset: Int, direction: OffsetDirection) extends SearchEntry {
  override def search(
    json: Json,
    entries: List[SearchEntry],
    jsonPath: JsonPath
  ): List[JsonPath] = json match {
    case Arr(vec, _) =>
      val index = direction match {
        case OffsetDirection.FromTop => offset
        case OffsetDirection.FromBottom => vec.length - 1 - offset
      }
      Try(vec(index)).toOption match {
        case Some(value) => SearchEntry.search(value, entries, jsonPath \ index)
        case None => Nil
      }
    case _ => Nil
  }
}

object ByOffset {
  implicit val rw: RW[ByOffset] = RW.from(
    r = t => obj("offset" -> num(t.offset), "direction" -> t.direction.json),
    w = j => ByOffset(j("offset").asInt, j("direction").as[OffsetDirection]),
    d = DefType.Obj(
      Some("fabric.search.ByOffset"),
      "offset" -> fabric.rw.intRW.definition,
      "direction" -> OffsetDirection.rw.definition
    )
  )
}
