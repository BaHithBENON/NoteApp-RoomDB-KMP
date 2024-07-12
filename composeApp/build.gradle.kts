import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    
    // Room
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            
            // Required when using NativeSQLiteDriver
            linkerOpts.add("-lsqlite3")
        }
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            // Material3 - Start
            implementation(compose.material3)
            //implementation(libs.material3.window)
            //implementation(libs.material3.adaptive.navigation)
            // Material3 - End
            
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            
            // Room
            // implementation("androidx.room:room-gradle-plugin")
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            
            // ViewModel
            implementation(libs.view.model)
            
            // Navigation
            implementation(libs.navigation)
            implementation(libs.voyager)
            implementation(libs.voyager.transition)
            implementation(libs.voyager.screenm)
            
            // DateTime
            implementation(libs.kotlinx.datetime)
            
            // Injection
            implementation(libs.kodein.di.compose)
            
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata")
        }
    }
}

android {
    namespace = "org.bahithbn.noteapp_roomdb"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/androidMain/res", "src/commonMain/resources")

    defaultConfig {
        applicationId = "org.bahithbn.noteapp_roomdb"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.bahithbn.noteapp_roomdb"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    // Google font
    implementation(libs.google.font)
    // ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    //add("ksp", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    //add("kspIosX64", libs.room.compiler)
    //add("kspCommonMainMetadata", libs.room.compiler)
    //add("kspIosArm64", libs.room.compiler)
    //add("kspIosSimulatorArm64", libs.room.compiler)
    
    // Animation
    implementation(libs.androidx.compose.animation)
    implementation(compose.animation)
}

room {
    schemaDirectory("$projectDir/schemas")
}

/*
// tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
//    if (name != "kspCommonMainKotlinMetadata" ) {
//        dependsOn("kspCommonMainKotlinMetadata")
//    }
// }
*/