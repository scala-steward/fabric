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

import fabric.define.DefType
import fabric.rw.RW
import fabric._

case class ByName(name: String) extends AnyVal with SearchEntry {
  override def search(
    json: Json,
    entries: List[SearchEntry],
    jsonPath: JsonPath
  ): List[JsonPath] = json match {
    case Obj(map) => map.get(name) match {
        case Some(value) => SearchEntry.search(value, entries, jsonPath \ name)
        case None => Nil
      }
    case _ => Nil
  }
}

object ByName {
  implicit val rw: RW[ByName] = RW.wrapped(
    key = "name",
    asJson = bn => str(bn.name),
    fromJson = j => ByName(j.asString),
    definition = DefType.Str
  )
}
