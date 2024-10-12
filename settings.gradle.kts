dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

includeBuild("build-logic")
include(":consteval-gradle-plugin")
include(":consteval-compiler")
include(":test")
