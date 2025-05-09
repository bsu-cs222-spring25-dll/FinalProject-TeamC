plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("net.minidev:json-smart:2.5.0")
    implementation("org.slf4j:slf4j-nop:2.0.11")
    testImplementation("ch.qos.logback:logback-classic:1.4.12")
}

javafx {
    version = "23.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("edu.bsu.cs.view.GUI") // Fully qualified name
}
javafx {
    version = "23.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}
