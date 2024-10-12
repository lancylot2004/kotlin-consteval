plugins {
    kotlin("jvm")
    id("consteval")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // For testing compilation results.
    testImplementation("dev.zacsweers.kctfork:core:0.5.1")

    // For directly accessing compiler plugin registrar.
    testImplementation(project(":consteval-compiler"))
}

tasks.test {
    useJUnitPlatform()
}
