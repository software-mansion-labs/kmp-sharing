# Module KMP Sharing

KMP Sharing provides a universal sharing function for Compose Multiplatform

## 🎯 Usage

```kotlin
@Composable
fun ShareButton() {
    val share = rememberShare()
    
    Button(
        onClick = {
            share(
                url = "file:///path/to/your/file.jpg",
                options = SharingOptions(
                    androidDialogTitle = "Share Image",
                    androidMimeType = "image/jpeg",
                    iosAnchor = Anchor(x = 100f, y = 100f, width = 200f, height = 50f)
                )
            )
        }
    ) {
        Text("Share")
    }
}
```

## 📦 Installation

### ✅ Recommended: Using Gradle Version Catalogs

First, add the library to your `gradle/libs.versions.toml` file:

```toml
[versions]
kmpSharing = "0.0.0"

[libraries]
swmansion-kmpSharing = { module = "com.swmansion.kmpmaps:kmp-sharing", version.ref = "kmpSharing" }
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
    implementation("com.swmansion.kmpmaps:kmp-sharing:0.0.0")
}
```