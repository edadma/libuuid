package io.github.edadma.libuuid.extern

import scala.scalanative.unsafe._, Nat._

@link("uuid")
@extern
object LibUUID:
  type uuid_tp = CArray[CUnsignedChar, Digit2[_1, _6]]
  // type uuid_tp = Ptr[uuid_t]

  def uuid_generate_random(out: uuid_tp): Unit = extern
  def uuid_generate(out: uuid_tp): Unit = extern
  def uuid_generate_time(out: uuid_tp): Unit = extern
  def uuid_generate_time_safe(out: uuid_tp): Unit = extern
  def uuid_unparse(uu: uuid_tp, out: CString): Unit = extern
  def uuid_parse(in: CString, uu: uuid_tp): CInt = extern
  def uuid_type(uu: uuid_tp): CInt = extern
