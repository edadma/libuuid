package io.github.edadma.libuuid

import scala.scalanative.unsafe._
import io.github.edadma.libuuid.extern.LibUUID._

def generateRandomUnparse: String =
  val binuuid = stackalloc[uuid_t]()
  val uuid = stackalloc[CChar](37)

  uuid_generate_random(binuuid)
  uuid_unparse(binuuid, uuid)
  fromCString(uuid)
