plugins {
    id("java")
    id("fabric-loom")
    id("maven-publish")
}

val modVersion: String by project
val minecraftVersion: String by project
val loaderVersion: String by project
val fabricVersion: String by project
val modmenuVersion: String by project

version = modVersion

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    implementation(project(":common"))
    include(project(":common"))

    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings(loom.officialMojangMappings())
    modApi("net.fabricmc:fabric-loader:${loaderVersion}")

    modApi("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")

    modCompileOnly("com.terraformersmc:modmenu:${modmenuVersion}")
    modLocalRuntime("com.terraformersmc:modmenu:${modmenuVersion}")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    val safeProps = project.properties
        .filterValues { it is String || it is Number || it is Boolean }

    inputs.properties(safeProps)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(safeProps)
    }
}

val targetJavaVersion: String by project

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"

    val targetVersion = targetJavaVersion.toIntOrNull() ?: 8
    if (targetVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetVersion)
    }
}

java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion.toInt()))
    }
    withSourcesJar()
}

// TODO: update the script to support uploading on maven central
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = rootProject.name
            groupId = group.toString() // group already defined in root gradle
            version = modVersion

            from(components["java"])
        }
    }

    repositories {
        mavenLocal()

        maven {
            name = "localBuildRepo"
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}

tasks.register("publishToLocal") {
    group = "publishing"
    description = "Publishes all Maven publications to the localBuildRepo."
    dependsOn("publishMavenJavaPublicationToLocalBuildRepoRepository")
}
