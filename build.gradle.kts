// build.gradle.kts (Project-Level)

plugins {
    // Здесь не нужно объявлять плагины
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
