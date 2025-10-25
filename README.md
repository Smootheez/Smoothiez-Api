# **Smoothiez API**

A foundational library mod that serves as the backbone for all of Smootheez’s other mods.
It provides shared utilities, common logic, and essential backend features to simplify mod development and ensure consistency across projects.

## **Usage Overview**

While Smoothiez API was originally developed for Smootheez’s personal projects, you’re welcome to use or integrate it into your own mods if it fits your needs.

## **Project Setup**

1. **Clone the repository**

   ```bash
   git clone https://github.com/Smootheez/Smoothiez-Api.git
   ```
2. **Build the desired module**

    * For the first build, run:

      ```bash
      ./gradlew :1.21.9:build
      ```

      Replace `1.21.9` with the target submodule name (e.g., the version your mod depends on).
    * The module to build depends on which one the `example` module uses.
3. **Reload the Gradle project** in your IDE (e.g., IntelliJ IDEA) after building.

## **Using Smoothiez API in Your Mod**

1. **Publish to your local Maven repository**

   ```bash
   ./gradlew :1.21.9:publishToMavenLocal
   ```

   Again, replace `1.21.9` with the module you built earlier.

2. **Add Smoothiez API as a dependency** in your mod’s `build.gradle.kts`:

   ```kotlin
   repositories {
       mavenLocal()
   }

   dependencies {
       modApi("io.github.smootheez:smoothiezapi:0.1.0+1.21.9")
       // or use a variable for flexibility:
       modApi("io.github.smootheez:smoothiezapi:${smoothiezApiVersion}")
   }
   ```

## 📥 Download

- **[Modrinth](https://modrinth.com/mod/smoothiez-api)**
- **[CurseForge](https://www.curseforge.com/minecraft/mc-mods/smoothiez-api)**

## 🐞 Issue Tracker

Found a bug or have a feature request?  
Please report it here: [**GitHub Issues**](https://github.com/Smootheez/Smoothiez-Api/issues)

## ☕ Support Me

If you enjoy this mod and want to support my work, consider donating:

[![ko-fi](https://raw.githubusercontent.com/Smootheez/Smootheez/7b16ed55570e49b9320e9cade5e572b271e9f1fe/assets/donation-kofi.svg)](https://ko-fi.com/smootheez)
[![paypal](https://raw.githubusercontent.com/Smootheez/Smootheez/7b16ed55570e49b9320e9cade5e572b271e9f1fe/assets/donation-paypal.svg)](https://paypal.me/smootheez)
