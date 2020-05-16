guardrailTasks in Compile := List(
  ScalaServer(file("petstore.yaml"), pkg="foo"),
)

val akkaVersion       = "10.0.14"
val catsVersion       = "2.0.0"
val circeVersion      = "0.13.0"
val scalatestVersion  = "3.0.7"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"         % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
  "io.circe"          %% "circe-core"        % circeVersion,
  "io.circe"          %% "circe-generic"     % circeVersion,
  "io.circe"          %% "circe-parser"      % circeVersion,
  "org.scalatest"     %% "scalatest"         % scalatestVersion % Test,
  "org.typelevel"     %% "cats-core"         % catsVersion
)
