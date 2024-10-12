package dev.lancy.consteval

import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
class ConstevalCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        // Template from `greeting-plugin`
//        FirExtensionRegistrarAdapter.registerExtension(GreetingFirExtensionRegistrar())
//        IrGenerationExtension.registerExtension(GreetingIrGenerationExtension(greetingMessage))
    }
}
