![KMP Sharing by Software Mansion](https://github.com/software-mansion/kmp-sharing/blob/main/docs/images/cover_image.png?raw=true)

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.20-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](./LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.swmansion.kmpsharing/kmp-sharing)](https://central.sonatype.com/artifact/com.swmansion.kmpsharing/kmp-sharing)

Universal sharing function for Compose Multiplatform.

## 🎯 See It in Action

Check out the sample project in the `/sample` directory for complete usage examples.

<div align="center">
    <video width="512" autoplay muted loop playsinline src="https://github.com/user-attachments/assets/59dcf160-7377-49b1-b9df-44f72afab086"></video>
</div>

## ✨ Features

* **Cross-platform compatibility** - Single API for both Android and iOS
* **Native performance** - Uses Android `Intent` system and iOS `UIActivityViewController`
* **File sharing support** - Share local files with proper MIME type detection
* **Customizable options** - Platform-specific customization options
* **Compose integration** - Built with Compose Multiplatform for modern UI development

## 🚀 Usage

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

## 📦 Instalation

For installation instructions visit our [dedicated documentation page](https://docs.swmansion.com/kmp-sharing/).


## 📚 Documentation

Check out our [dedicated documentation page](https://docs.swmansion.com/kmp-sharing/) for the API reference, and more.

## 🤝 Contributing

We welcome contributions! Please feel free to submit a pull request.

## KMP Sharing is created by Software Mansion

[![swm](https://logo.swmansion.com/logo?color=white&variant=desktop&width=150&tag=typegpu-github 'Software Mansion')](https://swmansion.com)

Since 2012 [Software Mansion](https://swmansion.com) is a software agency with
experience in building web and mobile apps. We are Core React Native
Contributors and experts in dealing with all kinds of React Native issues. We
can help you build your next dream product –
[Hire us](https://swmansion.com/contact/projects?utm_source=typegpu&utm_medium=readme).

Made by [@software-mansion](https://github.com/software-mansion) and
[community](https://github.com/software-mansion-labs/kmp-sharing/graphs/contributors) 💛
<br><br>
<a href="https://github.com/software-mansion-labs/kmp-sharing/graphs/contributors">
<img src="https://contrib.rocks/image?repo=software-mansion-labs/kmp-sharing" />
</a>
