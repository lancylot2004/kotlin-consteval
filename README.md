# kotlin-consteval

This is a very basic example implementation of constant evaluation in the Kotlin compiler. That is, the ability to
evaluate calls to a function at compile time in limited scenarios.

In this implementation, a function must satisfy the following conditions to be evaluated at compile:

1. The function name must begin with `eval`.
2. The function must not be abstract, expect, external, have receivers, or type arguments.
3. Input and output types to the function must be one of `Int`, `String`, or `Boolean`.
4. Statements within the function must be limited to if/when, while loops, and variable declarations.

This implementation is a proof of concept only and is far from usable. For example, it lacks the following features:

1. Evaluation of builtin functions, such as `Int.plus`.
2. Evaluation of functions with more complex control flow, such as for loops.
3. Ability to annotate or de-annotate certain functions for evaluation, or implement custom evaluation logic in code.
4. Other limitations implied above by restrictions, and more.

## Trying it Out

At this stage, proper programmatic testing of compiled code is not yet implemented. However, you can try out its
behaviour by adding to one of the test cases in `test/src/test/kotlin/dev/lancy/consteval`. Reviewing the generated
bytecode should reveal that the evaluation indeed does function.