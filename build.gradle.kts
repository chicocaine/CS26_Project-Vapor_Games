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

val junitVersion = "5.9.1" // Add this line to define the version

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaCompile> { // Well this shit Suppress Deprecation Warnings
    options.compilerArgs.add("-Xlint:-deprecation")
}

application {
    mainModule = "User_Interface"
    mainClass.set("User_Interface.ApplicationRunner")

    applicationDefaultJvmArgs = listOf(
        "--module-path", "${projectDir}/path/to/javafx-sdk-17.0.6/lib",
        "--add-modules", "javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media"
    )
}

javafx {
    version = "17.0.6"  // Adjust this to match the version in dependencies
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.web",
        "javafx.swing",
        "javafx.media"
    )
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("org.openjfx:javafx-controls:19")
    implementation("org.openjfx:javafx-fxml:19")

    // JavaFX-related libraries
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

    // Kotlin standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        kotlin.srcDir("src/main/kotlin")
        resources.srcDir("src/main/resources")
    }
}



jlink {
    imageZip = file("${project.buildDir}/distributions/app-${javafx.platform.classifier}.zip")
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "app"
    }
}

