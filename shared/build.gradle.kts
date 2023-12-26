plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.resourcesMultiplatform)
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)
            implementation(libs.kotlin.reflect)

            implementation(libs.odyssey.core)
            implementation(libs.odyssey.compose)

            implementation(libs.kviewmodel.core)
            implementation(libs.kviewmodel.compose)
            implementation(libs.kviewmodel.odyssey)

            implementation(libs.kodein)

            implementation(libs.kotlin.serialization)

            implementation(libs.ktor.core)
            implementation(libs.ktor.json)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.kotlinx.json)
            implementation(libs.ktor.logging)

            implementation(libs.settings)
            implementation(libs.settings.noarg)

            implementation(libs.libres.compose)
        }

        androidMain.dependencies {
            implementation(libs.android.material)
            implementation(libs.ktor.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.ios)
        }
    }
}

libres {
    generatedClassName = "AppRes"
    generateNamedArguments = true
    baseLocaleLanguageCode = "en"
}

android {
    namespace = "com.example.twcurrencyexchanger"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.toString()))
        }
    }
}
