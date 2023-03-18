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

object UUID:
  val NULL = new UUID(new Array[Byte](16))

class UUID private[libuuid] (private val arr: Array[Byte]) extends Equals:
  private def copy(uu: uuid_t): Unit =
    var i = 0

    while i < 16 do
      uu(i) = arr(i).toUByte
      i += 1

  def typ: Type =
    val uuid = stackalloc[uuid_t]()

    copy(uuid)
    uuid_type(uuid)

  def unparse: String =
    val binuuid = stackalloc[uuid_t]()
    val uuid = stackalloc[CChar](37)

    copy(binuuid)
    uuid_unparse(binuuid, uuid)
    fromCString(uuid)

  def unparseLower: String =
    val binuuid = stackalloc[uuid_t]()
    val uuid = stackalloc[CChar](37)

    copy(binuuid)
    uuid_unparse_lower(binuuid, uuid)
    fromCString(uuid)

  def unparseUpper: String =
    val binuuid = stackalloc[uuid_t]()
    val uuid = stackalloc[CChar](37)

    copy(binuuid)
    uuid_unparse_upper(binuuid, uuid)
    fromCString(uuid)

  def md5(name: String): UUID =
    val in = stackalloc[uuid_t]()
    val out = stackalloc[uuid_t]()

    copy(in)
    Zone(implicit z => uuid_generate_md5(out, in, toCString(name), name.length.toULong))
    fromUUID(out)

  def sha1(name: String): UUID =
    val in = stackalloc[uuid_t]()
    val out = stackalloc[uuid_t]()

    copy(in)
    Zone(implicit z => uuid_generate_sha1(out, in, toCString(name), name.length.toULong))
    fromUUID(out)

  def isNull: Boolean = arr forall (_ == 0)

  def canEqual(that: Any): Boolean = that.isInstanceOf[UUID]

  override def equals(obj: Any): Boolean =
    obj match
      case that: UUID => that.canEqual(this) && this.arr.sameElements(that.arr)
      case _          => false

  override def hashCode: Int = java.util.Arrays.hashCode(arr)

  override def toString: String =
    val t =
      typ match
        case Type.NIL      => "0 (nil)"
        case Type.TIME     => "1 (time)"
        case Type.SECURITY => "2 (security)"
        case Type.MD5      => "3 (MD5)"
        case Type.RANDOM   => "4 (random)"
        case Type.SHA1     => "5 (SHA1)"

    s"UUID[type $t; \"$unparse\"]"
end UUID

private def fromUUID(uu: uuid_t): UUID =
  val arr: Array[Byte] = new Array(16)
  var i = 0

  while i < 16 do
    arr(i) = uu(i).toByte
    i += 1

  new UUID(arr)

def generateRandomString: String =
  val binuuid = stackalloc[uuid_t]()
  val uuid = stackalloc[CChar](37)

  uuid_generate_random(binuuid)
  uuid_unparse(binuuid, uuid)
  fromCString(uuid)

def generateString: String =
  val binuuid = stackalloc[uuid_t]()
  val uuid = stackalloc[CChar](37)

  uuid_generate(binuuid)
  uuid_unparse(binuuid, uuid)
  fromCString(uuid)

def generateTimeString: String =
  val binuuid = stackalloc[uuid_t]()
  val uuid = stackalloc[CChar](37)

  uuid_generate_time(binuuid)
  uuid_unparse(binuuid, uuid)
  fromCString(uuid)

def generateTimeSafeString: String =
  val binuuid = stackalloc[uuid_t]()
  val uuid = stackalloc[CChar](37)

  uuid_generate_time_safe(binuuid)
  uuid_unparse(binuuid, uuid)
  fromCString(uuid)

def generateRandom: UUID =
  val binuuid = stackalloc[uuid_t]()

  uuid_generate_random(binuuid)
  fromUUID(binuuid)

def generate: UUID =
  val binuuid = stackalloc[uuid_t]()

  uuid_generate(binuuid)
  fromUUID(binuuid)

def generateTime: UUID =
  val binuuid = stackalloc[uuid_t]()

  uuid_generate_time(binuuid)
  fromUUID(binuuid)

def generateTimeSafe: UUID =
  val binuuid = stackalloc[uuid_t]()

  uuid_generate_time_safe(binuuid)
  fromUUID(binuuid)

def parse(uu: String): Option[UUID] =
  val binuuid = stackalloc[uuid_t]()

  if Zone(implicit z => uuid_parse(toCString(uu), binuuid)) != 0 then None
  else Some(fromUUID(binuuid))
