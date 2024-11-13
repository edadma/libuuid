name := "libuuid"

version := "0.0.2"

versionScheme := Some("early-semver")

scalaVersion := "3.5.2"

enablePlugins(ScalaNativePlugin)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:existentials",
)

organization := "io.github.edadma"

githubOwner := "edadma"

githubRepository := name.value

Global / onChangedBuildSource := ReloadOnSourceChanges

resolvers += Resolver.githubPackages("edadma")

licenses := Seq("ISC" -> url("https://opensource.org/licenses/ISC"))

homepage := Some(url("https://github.com/edadma/" + name.value))

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.19" % "test"

publishMavenStyle := true

Test / publishArtifact := false
