pluginManagement {
    plugins {
        id("fabric-loom") version extra["loomVersion"] as String
    }
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = extra["modid"] as String
include("common")
include("1.21.9")
include("example")