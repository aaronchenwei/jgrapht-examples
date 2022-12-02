plugins {
  java
}

group = "io.github.aaronchenwei.learning.jgrapht"
version = "0.0.1-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jgrapht:jgrapht-core:1.5.1")

  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}
