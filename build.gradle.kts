plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "explainfx"
version = "1.1"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("explainfx")
    mainClass.set("explainfx.ExplainFX")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")

    implementation("io.github.mkpaz:atlantafx-base:2.1.0")
    implementation("com.google.code.gson:gson:2.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "ExplainFX"
    }

    jpackage {
        imageName = "ExplainFX"
        installerName = "ExplainFX Installer"
        appVersion = "$version"

        icon = when {
            org.gradle.internal.os.OperatingSystem.current().isWindows -> "src/main/resources/icons/app.ico"
            org.gradle.internal.os.OperatingSystem.current().isMacOsX  -> "src/main/resources/installer/explainFX_icon_mac.icns"
            else -> "src/main/resources/icons/app.png"
        }

        installerOptions = listOf(
            "--description", "A simple, minimal drawing canvas app.",
            "--vendor", "Gufran Thakur"
        )
    }

}
