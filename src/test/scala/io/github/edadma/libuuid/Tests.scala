package io.github.edadma.libuuid

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class Tests extends AnyFreeSpec with Matchers:
  val uu1: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val uu2: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val uu3: Option[UUID] = parse("433afe80-8b40-41dc-9bde-61134946989e")
  val uu4: Option[UUID] = parse("115882e2-2404-41c2-a655-ecd79e58064e")

  "parse" in { uu1 should not be None }

  "inequality" in { uu1 should not equal uu4 }

  "equality 1" in { uu1 shouldEqual uu2 }

  "equality 2" in { uu2 shouldEqual uu3 }

  "equality: reflexivity" in { uu1 shouldEqual uu1 }

  "equality: symmetry" in { uu2 shouldEqual uu1 }

  "equality: transitivity" in { uu1 shouldEqual uu3 }

  "Type.RANDOM" in { uu1.get.typ shouldBe Type.RANDOM }

  "parse fail" in { parse("bad UUID") shouldBe None }

  "generateRandomString/parse/unparse" in {
    val u = generateRandomString
    val p = parse(u).get

    p.unparse shouldEqual u
  }

  "generateRandomString/typ" in {
    val u = generateRandomString
    val p = parse(u).get

    p.typ shouldBe Type.RANDOM
  }
