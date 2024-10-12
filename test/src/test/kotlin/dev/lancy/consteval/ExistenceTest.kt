package dev.lancy.consteval

import com.tschuchort.compiletesting.KotlinCompilation
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCompilerApi::class)
class ExistenceTest {
    @Test
    fun `plugin is being applied`() {
        val result = compile(sourceFile = getTestFile("ExampleGiven"))

        assert(result.exitCode == KotlinCompilation.ExitCode.OK)
    }
}
