group = "com.swmansion.kmpsharing"

version = "0.1.0"

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetBrains.compose)
    alias(libs.plugins.jetBrains.dokka)
    alias(libs.plugins.jetBrains.kotlin.multiplatform)
    alias(libs.plugins.jetBrains.kotlin.plugin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    explicitApi()
    jvmToolchain(17)
    androidTarget { publishLibraryVariants("release") }

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

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    pom {
        name = "KMP Sharing"
        description = "Universal sharing function for Compose Multiplatform."
        url = "https://github.com/software-mansion-labs/kmp-sharing"
        licenses {
            license {
                name = "The MIT License"
                url = "http://www.opensource.org/licenses/mit-license.php"
            }
        }
        scm {
            connection = "scm:git:git://github.com/software-mansion-labs/kmp-sharing.git"
            developerConnection = "scm:git:ssh://github.com/software-mansion-labs/kmp-sharing.git"
            url = "https://github.com/software-mansion-labs/kmp-sharing"
        }
        developers {
            developer {
                id = "arturgesiarz"
                name = "Artur Gęsiarz"
                email = "artur.gesiarz@swmansion.com"
            }
            developer {
                id = "marekkaput"
                name = "Marek Kaput"
                email = "marek.kaput@swmansion.com"
            }
            developer {
                id = "patrickmichalik"
                name = "Patrick Michalik"
                email = "patrick.michalik@swmansion.com"
            }
        }
    }
}
