plugins {
    kotlin("jvm")
    id("consteval")
}

dependencies {
    testImplementation(kotlin("test"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
