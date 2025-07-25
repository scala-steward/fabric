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

package fabric

/**
  * JsonType represents the possible types of Json
  */
sealed trait JsonType[T] {
  def is(`type`: JsonType[?]): Boolean = this == `type`
}

case object JsonType {
  case object Obj extends JsonType[fabric.Obj]
  case object Arr extends JsonType[fabric.Arr]
  case object Str extends JsonType[fabric.Str]
  case object Num extends JsonType[fabric.Num]
  case object NumInt extends JsonType[fabric.NumInt] {
    override def is(`type`: JsonType[?]): Boolean = super.is(`type`) || `type` == Num
  }
  case object NumDec extends JsonType[fabric.NumDec] {
    override def is(`type`: JsonType[?]): Boolean = super.is(`type`) || `type` == Num
  }
  case object Bool extends JsonType[fabric.Bool]
  case object Null extends JsonType[fabric.Null]
}
