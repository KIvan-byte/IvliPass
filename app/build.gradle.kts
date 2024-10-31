// app/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Подключение Kotlin плагина
}

android {
    namespace = "com.example.myapplication" // Замените на ваш пакет
    compileSdk = 33 // Установите нужную версию SDK

    defaultConfig {
        applicationId = "com.example.myapplication" // Замените на ваш пакет
        minSdk = 23 // Минимальный SDK должен быть >= 23 для EncryptedSharedPreferences
        targetSdk = 33 // Установите нужную версию SDK
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
    implementation("androidx.core:core:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation Component зависимости
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    // EncryptedSharedPreferences
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")

    // Kotlin stdlib (если используется)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")

    // Bcrypt для хеширования паролей
    implementation("at.favre.lib:bcrypt:0.9.0")

    // Тестовые зависимости
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


// Принудительное использование одной версии Kotlin для всех зависимостей
configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.10")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    }
}
