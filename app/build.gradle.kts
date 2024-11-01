// app/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Подключение Kotlin плагина
}

android {
    namespace = "com.example.myapplication" // Замените на ваш пакет
    compileSdk = 35 // Установите нужную версию SDK

    defaultConfig {
        applicationId = "com.example.myapplication" // Замените на ваш пакет
        minSdk = 23 // Минимальный SDK должен быть >= 23 для EncryptedSharedPreferences
        targetSdk = 35 // Установите нужную версию SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Или используйте нужную версию
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true // Если используете ViewBinding, иначе удалите или закомментируйте
    }
}

dependencies {
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Navigation Component зависимости
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // EncryptedSharedPreferences
    implementation(libs.androidx.security.crypto.ktx)

    // Kotlin stdlib (если используется)
    implementation(libs.kotlin.stdlib)

    // Bcrypt для хеширования паролей
    implementation(libs.bcrypt)

    // Тестовые зависимости
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}


// Принудительное использование одной версии Kotlin для всех зависимостей
configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.24")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.24")
    }
}
