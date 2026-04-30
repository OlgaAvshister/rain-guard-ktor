val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String = "1.1.1"
val postgresql_version: String = "42.7.7"

plugins {
    kotlin("jvm") version "2.3.0"
    id("io.ktor.plugin") version "3.4.2"
    kotlin("plugin.serialization") version "2.3.0"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.olga.avshister"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.cio.EngineMain"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-cio")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")

    // PostgreSQL Driver
    implementation("org.postgresql:postgresql:42.7.2")

    // HikariCP for connection pooling (optional but recommended)
    implementation("com.zaxxer:HikariCP:5.1.0")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:${exposed_version}")

    //postgresql
    implementation("org.postgresql:postgresql:${postgresql_version}")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
