import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.21"
  application
}

group = "io.github.aaronchenwei.learning.jgrapht"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jgrapht:jgrapht-core:1.5.1")
  implementation("org.jgrapht:jgrapht-io:1.5.1")

  implementation(kotlin("stdlib"))
  testImplementation(kotlin("test"))
  
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

application {
  mainClass.set("io.github.aaronchenwei.learning.jgrapht.HelloJGraphTKt") // The main class of the application
}
