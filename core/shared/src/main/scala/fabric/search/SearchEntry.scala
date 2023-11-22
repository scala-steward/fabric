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

import fabric.rw.RW
import fabric.{Json, JsonPath}

trait SearchEntry extends Any {
  def search(
    json: Json,
    entries: List[SearchEntry],
    jsonPath: JsonPath
  ): List[JsonPath]
}

object SearchEntry {
  implicit val rw: RW[SearchEntry] = RW.poly[SearchEntry]()(
    "byName" -> ByName.rw,
    "byOffset" -> ByOffset.rw,
    "byRegex" -> ByRegex.rw,
    "wildcard" -> RW.static("fabric.search.Wildcard", Wildcard),
    "doubleWildcard" -> RW.static("fabric.search.DoubleWildcard", DoubleWildcard)
  )

  def search(
    json: Json,
    entries: List[SearchEntry],
    jsonPath: JsonPath
  ): List[JsonPath] =
    if (entries.isEmpty) {
      List(jsonPath)
    } else {
      entries.head.search(json, entries.tail, jsonPath)
    }
}
