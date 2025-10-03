kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // --- LÍNEA AÑADIDA PARA SOLUCIONAR EL ERROR ---
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.calorias"
    compileSdk = 34 // Usamos la versión 34, la última estable y recomendada.

    defaultConfig {
        applicationId = "com.example.calorias"
        minSdk = 24
        targetSdk = 34 // Coincide con compileSdk.
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Versión del compilador de Compose compatible con las librerías estables
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // --- DEPENDENCIAS ESTABLES Y DEFINIDAS EXPLÍCITAMENTE ---

    // Librerías principales
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Compose Bill of Materials (BoM)
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))

    // Librerías de Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Navegación para Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Librería para cargar imágenes
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Dependencias de Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Dependencias de Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
