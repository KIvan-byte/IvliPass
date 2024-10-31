// settings.gradle.kts

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Укажите версию плагина Android Gradle Plugin (AGP)
        id("com.android.application") version "8.3.1"
        id("org.jetbrains.kotlin.android") version "1.8.10"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplication" // Замените на имя вашего проекта
include(":app") // Подключение модуля приложения
