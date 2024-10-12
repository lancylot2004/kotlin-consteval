plugins {
    kotlin("jvm")
    `java-gradle-plugin`
}

dependencies {
    compileOnly(kotlin("gradle-plugin-api"))
}

gradlePlugin {
    plugins {
        create("consteval") {
            id = "consteval"
            implementationClass = "dev.lancy.consteval.ConstevalPlugin"
        }
    }
}
