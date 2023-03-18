package io.github.edadma.libuuid.extern

import scala.scalanative.unsafe._, Nat._

@link("uuid")
@extern
object LibUUID:
  type uuid_t = CArray[CUnsignedChar, Digit2[_1, _6]]

  def uuid_generate_random(out: uuid_t): Unit = extern
  def uuid_generate(out: uuid_t): Unit = extern
  def uuid_generate_time(out: uuid_t): Unit = extern
  def uuid_generate_time_safe(out: uuid_t): Unit = extern
  def uuid_generate_md5(out: uuid_t, ns: uuid_t, name: CString, len: CSize): Unit = extern
  def uuid_generate_sha1(out: uuid_t, ns: uuid_t, name: CString, len: CSize): Unit = extern
  def uuid_unparse(uu: uuid_t, out: CString): Unit = extern
  def uuid_unparse_lower(uu: uuid_t, out: CString): Unit = extern
  def uuid_unparse_upper(uu: uuid_t, out: CString): Unit = extern
  def uuid_parse(in: CString, uu: uuid_t): CInt = extern
  def uuid_type(uu: uuid_t): CInt = extern
