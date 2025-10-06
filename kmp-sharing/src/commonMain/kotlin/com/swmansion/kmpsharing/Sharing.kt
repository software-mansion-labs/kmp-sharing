package com.swmansion.kmpsharing

import androidx.compose.runtime.Composable

/**
 * Remember a sharing function that uses the native sharing mechanism of the platform.
 *
 * This composable function returns a stable callback that opens the platform's native sharing
 * interface, allowing users to share the specified file through various apps and services available
 * on their device.
 *
 * ## Basic Usage
 *
 * ```kotlin
 * @Composable
 * fun ShareButton() {
 *     val share = rememberShare()
 *
 *     Button(
 *         onClick = {
 *             share(
 *                 url = "file:///path/to/your/file.jpg",
 *                 options = SharingOptions(
 *                     androidDialogTitle = "Share Image",
 *                     androidMimeType = "image/jpeg",
 *                     iosAnchor = Anchor(x = 100f, y = 100f, width = 200f, height = 50f)
 *                 )
 *             )
 *         }
 *     ) {
 *         Text("Share")
 *     }
 * }
 * ```
 *
 * ## File URL Requirements
 *
 * The returned function only supports local file URLs with the `file://` scheme:
 *
 * **Supported:**
 * - `file:///storage/emulated/0/Pictures/image.jpg`
 * - `file:///var/mobile/Containers/Data/Application/Documents/file.pdf`
 *
 * **Not Supported:**
 * - `https://example.com/file.jpg` (remote URLs)
 * - `content://...` (content URIs)
 * - Relative paths without `file://` scheme
 *
 * @return A stable callback function that accepts:
 *     - `url: String` - The file URL to share. Must be a local file URL (scheme: "file://")
 *     - `options: SharingOptions?` - Optional sharing configuration for platform-specific
 *       customization
 *
 * @throws IllegalArgumentException When the URL is invalid, file doesn't exist, or URL scheme is
 *   not "file://"
 * @throws RuntimeException When the sharing operation fails due to platform-specific errors
 */
@Composable public expect fun rememberShare(): (url: String, options: SharingOptions?) -> Unit
