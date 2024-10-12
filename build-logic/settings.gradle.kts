dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":consteval-gradle-plugin")
project(":consteval-gradle-plugin").projectDir = file("../consteval-gradle-plugin")
