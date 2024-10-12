package dev.lancy.consteval

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class ConstevalPlugin : KotlinCompilerPluginSupportPlugin {
    override fun getCompilerPluginId(): String = "consteval"

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean =
        kotlinCompilation.target.project.plugins
            .hasPlugin(ConstevalPlugin::class.java)

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(
            groupId = "dev.lancy",
            artifactId = "consteval-compiler",
            version = "1.0.0-alpha",
        )

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> =
        kotlinCompilation.target.project.provider { emptyList() }
}
