plugins {
    kotlin("jvm") version "2.2.21"
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
    id("org.jetbrains.kotlinx.kover") version "0.9.8"
}

group = "study.lotto"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:6.1.11")
    testImplementation("io.kotest:kotest-assertions-core:6.1.11")
    implementation(kotlin("reflect"))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

ktlint {
    ignoreFailures.set(false)
    verbose.set(true)
    outputToConsole.set(true)
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

tasks.test {
    useJUnitPlatform()
}
