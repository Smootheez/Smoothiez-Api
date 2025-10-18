plugins {
    id("java")
    id("fabric-loom")
}

val modVersion: String by project
val minecraftVersion: String by project
val loaderVersion: String by project
val modid: String by project
val fabricApi: String by project
val modmenuVersion: String by project

version = modVersion

loom {
    splitEnvironmentSourceSets()
    mods {
        create(modid) {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.named("client").get())
        }
    }
}


repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    modImplementation(project(":1.21.9"))
    implementation(project(":common"))

    minecraft("com.mojang:minecraft:${minecraftVersion}")
    modImplementation("net.fabricmc:fabric-loader:${loaderVersion}")
    mappings(loom.officialMojangMappings())

    modCompileOnly("net.fabricmc.fabric-api:fabric-api:${fabricApi}")
    modLocalRuntime("net.fabricmc.fabric-api:fabric-api:${fabricApi}")

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
