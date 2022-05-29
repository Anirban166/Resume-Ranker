import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

group = "org.example.resumescanner"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)

    // https://mvnrepository.com/artifact/org.apache.poi/poi
    implementation("org.apache.poi:poi:5.2.2")

    // https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml-schemas
    implementation("org.apache.poi:poi-ooxml-schemas:4.1.2")

    // https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    // https://mvnrepository.com/artifact/org.apache.poi/poi-scratchpad
    implementation("org.apache.poi:poi-scratchpad:5.2.2")

    // https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
    implementation("org.apache.pdfbox:pdfbox:2.0.26")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ResumeScannerCompose"
            packageVersion = "1.0.0"
        }
    }
}