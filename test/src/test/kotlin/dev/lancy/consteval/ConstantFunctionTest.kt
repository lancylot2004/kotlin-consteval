package dev.lancy.consteval

import com.tschuchort.compiletesting.KotlinCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCompilerApi::class)
class ConstantFunctionTest {
    @Test
    fun `correct transformation of return constant`() {
        val result = compile(sourceFile = getTestFile("ExampleConstant"))

        assert(result.exitCode == KotlinCompilation.ExitCode.OK)

        // TODO: Programmatically check compiled code.
    }
}