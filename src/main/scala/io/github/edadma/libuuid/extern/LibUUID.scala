package io.github.edadma.libuuid

import scala.scalanative.unsafe._, Nat._

@link("uuid")
@extern
object LibUUID:
  type uuid_t = CArray[CUnsignedChar, Digit2[_1, _6]]
  type uuid_tp = Ptr[uuid_t]
