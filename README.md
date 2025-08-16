# libuuid

A Scala Native wrapper for the C libuuid library, providing UUID generation and manipulation functionality.

## Overview

This library provides a complete Scala Native interface to the system's libuuid library, enabling UUID generation, parsing, and manipulation with native performance. It supports all major UUID types including random, time-based, and namespace-based UUIDs.

## Features

- **UUID Generation**: Random, time-based, and time-safe UUID generation
- **UUID Parsing**: Parse UUID strings into UUID objects
- **String Formatting**: Multiple string representations (normal, lowercase, uppercase)
- **Namespace UUIDs**: MD5 and SHA1-based namespace UUID generation
- **Type Detection**: Identify UUID version/type
- **Null UUID Support**: Built-in null UUID constant

## Installation

Add the following to your `build.sbt`:

```scala
libraryDependencies += "io.github.edadma" %%% "libuuid" % "0.0.1"
```

**Note**: This library requires the system libuuid library to be installed:

- **Ubuntu/Debian**: `sudo apt-get install uuid-dev`
- **CentOS/RHEL**: `sudo yum install libuuid-devel`
- **macOS**: Usually available by default
- **Alpine**: `apk add util-linux-dev`

## Quick Start

```scala
import io.github.edadma.libuuid._

// Generate a random UUID string
val uuidString = generateRandomString
println(uuidString) // e.g., "433afe80-8b40-41dc-9bde-61134946989e"

// Parse a UUID string
val uuid = parse(uuidString).get
println(uuid.typ) // Type.RANDOM

// Generate UUID objects
val randomUuid = generateRandom
val timeUuid = generateTime
```

## API Reference

### UUID Generation

#### String Generation
```scala
// Generate UUID strings directly
def generateRandomString: String      // Random UUID
def generateString: String           // Random UUID (alias)
def generateTimeString: String       // Time-based UUID
def generateTimeSafeString: String   // Time-safe UUID
```

#### UUID Object Generation
```scala
// Generate UUID objects
def generateRandom: UUID      // Random UUID
def generate: UUID           // Random UUID (alias)
def generateTime: UUID       // Time-based UUID
def generateTimeSafe: UUID   // Time-safe UUID
```

### UUID Parsing and Formatting

```scala
// Parse UUID from string
def parse(uu: String): Option[UUID]

// String representations
uuid.unparse: String       // Standard format
uuid.unparseLower: String  // Lowercase
uuid.unparseUpper: String  // Uppercase
```

### UUID Types

The library supports detection of UUID types:

```scala
val uuid = parse("433afe80-8b40-41dc-9bde-61134946989e").get
uuid.typ match {
  case Type.NIL      => "Nil UUID"
  case Type.TIME     => "Time-based UUID"
  case Type.SECURITY => "Security UUID"
  case Type.MD5      => "MD5 namespace UUID"
  case Type.RANDOM   => "Random UUID"
  case Type.SHA1     => "SHA1 namespace UUID"
}
```

### Namespace UUIDs

Generate namespace-based UUIDs using MD5 or SHA1:

```scala
val baseUuid = generateRandom
val md5Uuid = baseUuid.md5("example-name")
val sha1Uuid = baseUuid.sha1("example-name")

println(md5Uuid.typ)  // Type.MD5
println(sha1Uuid.typ) // Type.SHA1
```

### Null UUID

```scala
// Check for null UUID
val nullUuid = UUID.NULL
println(nullUuid.isNull) // true

// Compare with parsed null UUID
val parsed = parse("00000000-0000-0000-0000-000000000000").get
println(parsed == UUID.NULL) // true
```

## Usage Examples

### Basic UUID Operations

```scala
import io.github.edadma.libuuid._

@main def example(): Unit = {
  // Generate a random UUID
  val uuid1 = generateRandom
  println(s"Generated: $uuid1")
  
  // Convert to string and parse back
  val uuidString = uuid1.unparse
  val uuid2 = parse(uuidString).get
  
  // Verify they're equal
  println(s"Equal: ${uuid1 == uuid2}") // true
  
  // Check UUID type
  println(s"Type: ${uuid2.typ}")
  
  // Generate time-based UUID
  val timeUuid = generateTime
  println(s"Time UUID: $timeUuid")
}
```

### Namespace UUID Generation

```scala
import io.github.edadma.libuuid._

// Create a namespace UUID
val namespace = generateRandom

// Generate consistent UUIDs for the same name
val uuid1 = namespace.md5("my-resource")
val uuid2 = namespace.md5("my-resource")
val uuid3 = namespace.md5("different-resource")

println(uuid1 == uuid2) // true - same name produces same UUID
println(uuid1 == uuid3) // false - different names produce different UUIDs
```

### Error Handling

```scala
import io.github.edadma.libuuid._

// Parse returns Option[UUID]
parse("invalid-uuid") match {
  case Some(uuid) => println(s"Parsed: $uuid")
  case None => println("Invalid UUID format")
}

// Safe parsing with validation
def validateUuid(input: String): Either[String, UUID] = {
  parse(input) match {
    case Some(uuid) if !uuid.isNull => Right(uuid)
    case Some(_) => Left("UUID is null")
    case None => Left("Invalid UUID format")
  }
}
```

## Requirements

- Scala Native 0.5.x
- System libuuid library
- Scala 3.7.2+

## License

ISC License - see [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests.

## Links

- [GitHub Repository](https://github.com/edadma/libuuid)
- [Scala Native Documentation](https://scala-native.org/)
- [libuuid man pages](https://man7.org/linux/man-pages/man3/uuid.3.html)