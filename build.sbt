guardrailTasks in Compile := List(
  Client(file("petstore.yaml"), pkg="foo"),
)

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"
