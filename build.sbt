guardrailTasks in Compile := List(
  ScalaServer(file("petstore.yaml"), pkg="foo"),
)

val akkaVersion       = "2.6.5"
val akkaHttpVersion   = "10.1.12"
val catsVersion       = "2.0.0"
val circeVersion      = "0.13.0"
val scalatestVersion  = "3.0.7"

scalacOptions ++= Seq("-Ypartial-unification", "-deprecation")

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"        % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"       % akkaVersion,
  "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "io.circe"          %% "circe-core"        % circeVersion,
  "io.circe"          %% "circe-generic"     % circeVersion,
  "io.circe"          %% "circe-parser"      % circeVersion,
  "org.scalatest"     %% "scalatest"         % scalatestVersion % Test,
  "org.typelevel"     %% "cats-core"         % catsVersion
)

addCompilerPlugin(scalafixSemanticdb)
