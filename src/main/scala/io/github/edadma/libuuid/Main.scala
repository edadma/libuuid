package io.github.edadma.libuuid

@main def run(): Unit =
  val u = generateRandomUnparse

  println(u)

  val p = parse(u)

  println(p)
