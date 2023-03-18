package io.github.edadma.libuuid

import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import io.github.edadma.libuuid.extern.LibUUID._

implicit class Type(val value: CInt) extends AnyVal

object Type:
  final val NIL = new Type(0)
  final val TIME = new Type(1)
  final val SECURITY = new Type(2)
  final val MD5 = new Type(3)
  final val RANDOM = new Type(4)
  final val SHA1 = new Type(5)

class UUID private[libuuid] (val arr: Array[Byte]):
  private def copy(uu: uuid_tp): Unit =
    var i = 0

    while i < 16 do
      uu(i) = arr(i).toUByte
      i += 1

  def typ: Type =
    val uuid = stackalloc[uuid_tp]()

    copy(uuid)
    uuid_type(uuid)

  def unparse: String =
    val binuuid = stackalloc[uuid_tp]()
    val uuid = stackalloc[CChar](37)

    copy(binuuid)
    uuid_unparse(binuuid, uuid)
    fromCString(uuid)

  override def toString: String = s"UUID[type ${typ.value}; \"$unparse\"]"
end UUID

private def fromUUID(uu: uuid_tp): UUID =
  val arr: Array[Byte] = new Array(16)
  var i = 0

  while i < 16 do
    arr(i) = uu(i).toByte
    i += 1

  new UUID(arr)

def generateRandomString: String =
  val binuuid = stackalloc[uuid_tp]()
  val uuid = stackalloc[CChar](37)

  uuid_generate_random(binuuid)
  uuid_unparse(binuuid, uuid)
  fromCString(uuid)

def generateRandom: UUID =
  val binuuid = stackalloc[uuid_tp]()

  uuid_generate_random(binuuid)
  fromUUID(binuuid)

def parse(uu: String): Option[UUID] =
  val binuuid = stackalloc[uuid_tp]()

  if Zone(implicit z => uuid_parse(toCString(uu), binuuid)) != 0 then None
  else Some(fromUUID(binuuid))
