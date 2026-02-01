Compile / guardrailTasks := List(
  ScalaServer(file("petstore.yaml"), pkg="foo"),
)

val akkaVersion       = "2.6.15"
val akkaHttpVersion   = "10.2.4"
val catsVersion       = "2.6.1"
val circeVersion      = "0.14.1"
val scalatestVersion  = "3.2.19"
val jaxbApiVersion    = "2.3.1"

scalacOptions ++= Seq("-Ypartial-unification", "-deprecation")

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.30"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"        % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"       % akkaVersion,
  "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "io.circe"          %% "circe-core"        % circeVersion,
  "io.circe"          %% "circe-generic"     % circeVersion,
  "io.circe"          %% "circe-parser"      % circeVersion,
  "javax.xml.bind"    %  "jaxb-api"          % jaxbApiVersion,
  "org.scalatest"     %% "scalatest"         % scalatestVersion % Test,
  "org.typelevel"     %% "cats-core"         % catsVersion,
)
