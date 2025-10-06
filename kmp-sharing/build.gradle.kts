group = "com.swmansion.kmpsharing"

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetBrains.compose)
    alias(libs.plugins.jetBrains.dokka)
    alias(libs.plugins.jetBrains.kotlin.multiplatform)
    alias(libs.plugins.jetBrains.kotlin.plugin.compose)
}

kotlin {
    explicitApi()
    jvmToolchain(17)
    androidTarget()

    compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "kmp-sharing"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.jetBrains.androidX.lifecycle.runtimeCompose)
            implementation(libs.jetBrains.androidX.lifecycle.viewmodelCompose)
        }
        androidMain.dependencies { implementation(libs.androidX.core.ktx) }
    }
}

android {
    namespace = "com.swmansion.kmpsharing"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dokka {
    dokkaSourceSets {
        configureEach {
            moduleName = "KMP Sharing"
            externalDocumentationLinks {
                register("coroutines") { url("https://kotlinlang.org/api/kotlinx.coroutines") }
            }
            includes.from("$rootDir/docs/QUICK_START.md")
        }
    }

    pluginsConfiguration.html {
        footerMessage =
            """
            © <a href="https://swmansion.com" rel="noopener noreferrer" target="_blank">Software Mansion</a> 2025. 
            All trademarks and copyrights belong to their respective owners.
            """
                .trimIndent()
    }
}
