package com.swmansion.kmpsharing
public data class SharingOptions(
    val iosAnchor: Anchor? = null,
    val iosUTI: String? = null,
    val androidDialogTitle: String? = null,
    val androidMimeType: String? = null,
)

public data class Anchor(val height: Float, val width: Float, val x: Float, val y: Float)
