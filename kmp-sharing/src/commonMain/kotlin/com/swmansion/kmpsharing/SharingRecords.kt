package com.swmansion.kmpsharing

/**
 * Configuration options for sharing behavior across platforms.
 *
 * @param iosAnchor Position and size for iOS popover presentation (iPad only)
 * @param iosUTI iOS Uniform Type Identifier for file type recognition
 * @param androidDialogTitle Title for Android share dialog
 * @param androidMimeType MIME type override for Android (auto-detected if null)
 */
public data class SharingOptions(
    val iosAnchor: Anchor? = null,
    val iosUTI: String? = null,
    val androidDialogTitle: String? = null,
    val androidMimeType: String? = null,
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
