Running the task in the sbt console fails:
```
sbt:bug> guardrail
Spec file petstore.yaml is incorrectly formatted.
[error] (compile:guardrail) com.twilio.guardrail.sbt.CodegenFailedException
[error] Total time: 1 s, completed Feb 24, 2019 12:24:37 PM
```

Running the task directly via `consoleProject` succeeds:
```
sbt:bug> consoleProject
[debug] Waiting for threads to exit or System.exit to be called.
[debug] Getting org.scala-sbt:compiler-bridge_2.12:1.0.5:compile for Scala 2.12.4
[debug] Waiting for thread run-main-0 to terminate.
[debug] Getting org.scala-sbt:compiler-bridge_2.12:1.0.5:compile for Scala 2.12.4
[info] Starting scala interpreter...
Welcome to Scala 2.12.4 (OpenJDK 64-Bit Server VM, Java 1.8.0_121).
Type in expressions for evaluation. Or try :help.
import [...]

scala> (com.twilio.guardrail.sbt.Keys.guardrail in Compile).eval
Warning: Using `tags` to define package membership is deprecated in favor of the `x-scala-package` vendor extension
Warning: Using `tags` to define package membership is deprecated in favor of the `x-scala-package` vendor extension
Warning: Using `tags` to define package membership is deprecated in favor of the `x-scala-package` vendor extension
Error: Unknown HTTP type: default
[error] (compile:guardrail) com.twilio.guardrail.sbt.CodegenFailedException
sbt.Incomplete: com.twilio.guardrail.sbt.CodegenFailedException
  at sbt.Incomplete.copy(Incomplete.scala:24)
```

(`Error: Unknown HTTP type: default` is intentional, that comes from the guardrail core as a response to poorly specified OpenAPI routes)
