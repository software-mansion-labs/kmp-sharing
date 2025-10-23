## 📦 Installation

### ✅ Recommended: Using Gradle Version Catalogs

First, add the library to your `gradle/libs.versions.toml` file:

```toml
[versions]
kmpSharing = "0.1.0"

[libraries]
swmansion-kmpSharing = { module = "com.swmansion.kmpsharing:kmp-sharing", version.ref = "kmpSharing" }
```

Then add it to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.swmansion.kmpSharing)
}
```

### 🔧 Alternative: Direct Dependency Declaration

If you're not using Gradle version catalogs, you can add the library directly to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.swmansion.kmpsharing:kmp-sharing:0.1.0")
}
```
