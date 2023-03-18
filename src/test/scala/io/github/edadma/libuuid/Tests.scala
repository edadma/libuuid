package io.github.edadma.libuuid

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class Tests extends AnyFreeSpec with Matchers:
  "parse" in {
    parse("433afe80-8b40-41dc-9bde-61134946989e") shouldNot be(None)
  }

  "Type.RANDOM" in {
    parse("433afe80-8b40-41dc-9bde-61134946989e").get.typ shouldBe Type.RANDOM
  }

  "parse fail" in {
    parse("bad UUID") shouldBe None
  }

  "generateRandomString/parse/unparse" in {
    val u = generateRandomString
    val p = parse(u).get

    p.unparse shouldBe u
  }

  "generateRandomString/typ" in {
    val u = generateRandomString
    val p = parse(u).get

    p.typ shouldBe Type.RANDOM
  }
