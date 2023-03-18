package io.github.edadma.libuuid

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class Tests extends AnyFreeSpec with Matchers:
  val v41: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val v42: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val v43: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val v44: Option[UUID] = parse("115882e2-2404-41c2-a655-ecd79e58064e")

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
