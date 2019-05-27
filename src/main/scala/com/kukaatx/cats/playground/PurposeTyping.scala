package com.kukaatx.cats.playground

import java.net.URLEncoder

object PurposeTyping {
  case class RawString(value: String)

  object UrlEncodedString {
    def apply(rawString: RawString): UrlEncodedString = new UrlEncodedString(rawString)
  }

  // Not a case class because we don't want rawString to be a val field.
  class UrlEncodedString(rawString: RawString) {
    val value: String = URLEncoder.encode(rawString.value, "UTF-8")
  }

  def render(urlEncodedString: UrlEncodedString): Unit = {
    // Pretend an HTML injection attack was possible here.
    println(s"It is: ${urlEncodedString.value}")
  }

  def doStuff(): Unit = {
    val maliciousString = "<a href=\"http://nigerian.prince.com\">Click Here!</a>"

    // these won't even compile
    // render(maliciousString)
    // render(RawString(maliciousString))

    // But this will
    // Having type representations of each state allows you to clearly pass them around
    // in different forms. Would be amazing to do this with user IDs.
    render(UrlEncodedString(RawString(maliciousString)))
  }

  // other examples
  case class RawInteger(value: Int)

  object NaturalNumber {
    def coerce(rawInteger: RawInteger): NaturalNumber = if (rawInteger.value < 1) {
      new NaturalNumber(RawInteger(1))
    } else {
      new NaturalNumber(rawInteger)
    }

    def apply(rawInteger: RawInteger): NaturalNumber = new NaturalNumber(rawInteger)
  }

  class NaturalNumber(rawInteger: RawInteger) {
    if (rawInteger.value <= 0) {
      throw new IllegalArgumentException(s"Value $rawInteger is not a natural number")
    }

    val value: Int = rawInteger.value
  }
}
