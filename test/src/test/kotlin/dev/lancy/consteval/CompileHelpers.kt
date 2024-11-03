package dev.lancy.consteval

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
fun compile(sourceFiles: List<SourceFile>, verbose: Boolean = false): JvmCompilationResult = KotlinCompilation().apply {
    sources = sourceFiles
    compilerPluginRegistrars = listOf(ConstevalCompilerPluginRegistrar())
    inheritClassPath = true
    this.verbose = verbose
}.compile()

@OptIn(ExperimentalCompilerApi::class)
fun compile(sourceFile: SourceFile, verbose: Boolean = false): JvmCompilationResult =
    compile(listOf(sourceFile), verbose)

private object Whatever

/**
 * Gets a testing source file from the `resources` set, under the `sources` folder.
 *
 * @param name The name of the source file, without the `.kt` extension.
 * @return The [SourceFile] object.
 */
fun getTestFile(name: String): SourceFile = File(
    Whatever.javaClass.getResource("/sources/$name.kt")?.toURI()
        ?: throw IllegalArgumentException("Source file $name not found in the [sources] folder in [resources].")
).let { SourceFile.fromPath(it) }