import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  id("org.springframework.boot") version "2.7.5"
  id("io.spring.dependency-management") version "1.0.15.RELEASE"
  id("java")
}

group = "ee.carlrobert.note"

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

val springDocVersion        = "1.6.13"
val testContainersVersion   = "1.17.6"
val junitVersion            = "5.9.1"
val assertjVersion          = "3.23.1"

dependencies {
  implementation(platform(SpringBootPlugin.BOM_COORDINATES))
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.session:spring-session-core")
  implementation("org.springframework.session:spring-session-jdbc")

  implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.fasterxml.jackson.core:jackson-databind")
  runtimeOnly("org.flywaydb:flyway-core")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.testcontainers:postgresql:$testContainersVersion")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
  testImplementation("org.assertj:assertj-core:$assertjVersion")
}

repositories {
  mavenCentral()
}

tasks.withType<Test> {
  useJUnitPlatform()

  testLogging {
    events(
      TestLogEvent.STARTED,
      TestLogEvent.FAILED,
      TestLogEvent.PASSED,
      TestLogEvent.SKIPPED
    )
    exceptionFormat = TestExceptionFormat.FULL
    showExceptions = true
    showCauses = true
    showStackTraces = true
  }
}
