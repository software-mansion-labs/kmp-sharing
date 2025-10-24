package com.swmansion.kmpsharing

/**
 * Platform-specific configuration for Android sharing.
 *
 * @param dialogTitle Title for Android share dialog
 * @param mimeType MIME type override for Android (auto-detected if null)
 * @param previewData Preview image path for Android URL and text sharing
 */
public data class AndroidSharingOptions(
    val dialogTitle: String? = null,
    val mimeType: String? = null,
    val previewData: String? = null,
)

/**
 * Platform-specific configuration for iOS sharing.
 *
 * @param anchor Position and size for iOS popover presentation (iPad only)
 * @param uti Uniform Type Identifier for file type recognition
 */
public data class IosSharingOptions(val anchor: Anchor? = null, val uti: String? = null)

/**
 * Configuration options for sharing behavior across platforms.
 *
 * @param android Platform-specific options for Android
 * @param ios Platform-specific options for iOS
 */
public data class SharingOptions(
    val android: AndroidSharingOptions? = null,
    val ios: IosSharingOptions? = null,
)

/**
 * Represents the position and size for iOS popover presentation.
 *
 * @param height Height of the anchor rectangle in points
 * @param width Width of the anchor rectangle in points
 * @param x _x_-coordinate of the anchor rectangle in points
 * @param y _y_-coordinate of the anchor rectangle in points
 */
public data class Anchor(val height: Float, val width: Float, val x: Float, val y: Float)

/** Enumeration of supported sharing data types. */
internal enum class DataType {
    FILE,
    CONTENT,
    LINK,
    TEXT,
}
