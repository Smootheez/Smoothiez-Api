import io.github.smootheez.LoaderType
import io.github.smootheez.curseforge.EnvironmentType

plugins {
    id("fabric-conventions")
    id("io.github.smootheez.mc-mod-publisher") version "1.0.0"
}

val fabricVersion: String by project
val modmenuVersion: String by project
val modName: String by project
val minecraftVersion: String by project
val modVersion: String by project

val modReleaseType: String by project
val modrinthId: String by project
val curseforgeId: String by project

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    modApi("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")

    modCompileOnly("com.terraformersmc:modmenu:${modmenuVersion}")
    modLocalRuntime("com.terraformersmc:modmenu:${modmenuVersion}")
}

val modMinecraftVersion = "$modVersion+$minecraftVersion"

mcModPublisher {
    displayName = "$modName $modMinecraftVersion"
    version = modMinecraftVersion
    releaseType = modReleaseType
    changelog = file("../changelogs/$modMinecraftVersion.md").readText()
    files.from(tasks.named("remapJar"))
    gameVersions.addAll(listOf("1.21.11"))
    loaders.addAll(listOf(LoaderType.FABRIC))

    curseforge {
        token = System.getenv("CURSEFORGE_TOKEN")
        projectId = curseforgeId
        environmentType = listOf(EnvironmentType.CLIENT)
    }

    modrinth {
        token = System.getenv("MODRINTH_TOKEN")
        projectId = modrinthId
    }
}