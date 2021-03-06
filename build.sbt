import Dependencies._

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val circeVersion = "0.12.3"

libraryDependencies ++= Seq("io.circe" %% "circe-core", "io.circe" %% "circe-generic", "io.circe" %% "circe-parser")
  .map(_ % circeVersion)

libraryDependencies += "org.scala-graph" %% "graph-core"        % "1.13.2"
libraryDependencies += "org.scala-graph" %% "graph-constrained" % "1.13.2"
libraryDependencies += "org.scala-graph" %% "graph-dot"         % "1.13.0"

lazy val root = (project in file(".")).settings(
  name := "infrascript",
  libraryDependencies += scalaTest % Test,
  semanticdbEnabled := true,     // enable SemanticDB
  semanticdbVersion := "4.3.10", // scalafixSemanticdb.revision, // use Scalafix compatible version
  scalacOptions += "-Wunused",   // required by `RemoveUnused` rule
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
