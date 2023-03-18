package io.github.edadma.libuuid

@main def run(): Unit =
  val u = generateRandomString

  println(u)

  val p = parse(u)

  println(p)
  println(p.get.typ == Type.TIME)
