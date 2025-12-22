{
  description = "guardrail-sample-http4s-zio";

  inputs.upstream.url = "github:guardrail-dev/guardrail";

  outputs = { upstream, ... }: upstream.outputs;
}
