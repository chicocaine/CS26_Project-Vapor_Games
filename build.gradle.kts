plugins {
    kotlin("jvm") version "1.8.10"
    id("java")
    id("application")
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.9.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule = "User_Interface"
    mainClass.set("User_Interface.VGMainProgramApplication")
}

javafx {
    version = "22"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing", "javafx.media")
}

dependencies {

    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("com.zaxxer:HikariCP:6.2.0")
    implementation("mysql:mysql-connector-java:8.0.28")

    implementation("org.controlsfx:controlsfx:11.1.2")
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0") {
        exclude(group = "org.openjfx")
    }
    implementation("net.synedra:validatorfx:0.4.0") {
        exclude(group = "org.openjfx")
    }
    implementation("org.kordamp.ikonli:ikonli-javafx:12.3.1")
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    implementation("eu.hansolo:tilesfx:11.48") {
        exclude(group = "org.openjfx")
    }
    implementation("com.github.almasb:fxgl:17.3") {
        exclude(group = "org.openjfx")
        exclude(group = "org.jetbrains.kotlin")
    }

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

}

tasks.test {
    useJUnitPlatform()
}

tasks.named<ProcessResources>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        resources.srcDirs("src/main/resources")
    }
}

jlink {
    imageZip = file("${buildDir}/distributions/app-${javafx.platform.classifier}.zip")
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "app"
    }
}
