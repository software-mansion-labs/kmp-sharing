package com.swmansion.kmpsharing

internal fun getContentType(content: String): ContentType {
    val trimmed = content.trim()

    return when {
        trimmed.startsWith("file://") -> ContentType.FILE
        trimmed.startsWith("http://") || trimmed.startsWith("https://") -> ContentType.LINK
        else -> ContentType.TEXT
    }
}
