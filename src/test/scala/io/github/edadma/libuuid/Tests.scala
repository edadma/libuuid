package io.github.edadma.libuuid

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class Tests extends AnyFreeSpec with Matchers:
  val v41: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val v42: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val v43: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val v44: Option[UUID] = parse("115882e2-2404-41c2-a655-ecd79e58064e")
  val v0: Option[UUID] = parse("00000000-0000-0000-0000-000000000000")

  "parse" in { v41 should not be None }

  "inequality" in { v41 should not equal v44 }

  "equality 1" in { v41 shouldEqual v42 }

  "equality 2" in { v42 shouldEqual v43 }

  "equality: reflexivity" in { v41 shouldEqual v41 }

  "equality: symmetry" in { v42 shouldEqual v41 }

  "equality: transitivity" in { v41 shouldEqual v43 }

  "Type.RANDOM" in { v41.get.typ shouldBe Type.RANDOM }

  "parse fail" in { parse("bad UUID") shouldBe None }

  "generateRandomString/parse/unparse" in {
    val u = generateRandomString
    val p = parse(u).get

    p.unparse shouldEqual u
  }

  "generateRandomString/typ" in { parse(generateRandomString).get.typ shouldBe Type.RANDOM }

  "generateString/typ" in { parse(generateString).get.typ shouldBe Type.RANDOM }

  "generateTimeString/typ" in { parse(generateTimeString).get.typ shouldBe Type.TIME }

  "generateTimeSafeString/typ" in { parse(generateTimeSafeString).get.typ shouldBe Type.TIME }

  "UUID.NULL 1" in { UUID.NULL.isNull shouldBe true }

  "UUID.NULL 2" in { UUID.NULL shouldEqual v0.get }

  "isNull" in { v41.get.isNull shouldBe false }

  "md5/typ" in { v41.get.md5("foo").typ shouldBe Type.MD5 }

  "md5 equality" in { v41.get.md5("foo") shouldEqual v42.get.md5("foo") }

  "md5 inequality" in { v41.get.md5("foo") should not equal v41.get.md5("bar") }

  "sha1/typ" in { v41.get.sha1("foo").typ shouldBe Type.SHA1 }

  "sha1 equality" in { v41.get.sha1("foo") shouldEqual v42.get.sha1("foo") }

  "sha1 inequality" in { v41.get.sha1("foo") should not equal v41.get.sha1("bar") }
