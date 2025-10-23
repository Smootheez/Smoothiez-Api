plugins {
    id("fabric-conventions")
}

val fabricVersion: String by project
val modmenuVersion: String by project

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    implementation(project(":common"))
    include(project(":common"))

    modApi("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")

    modCompileOnly("com.terraformersmc:modmenu:${modmenuVersion}")
    modLocalRuntime("com.terraformersmc:modmenu:${modmenuVersion}")
}