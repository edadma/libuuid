package io.github.edadma.libuuid

@main def run(): Unit =
  val u = generateString

  println(u)

  val p = parse(u)

  println(p)

  println(UUID.NULL)
