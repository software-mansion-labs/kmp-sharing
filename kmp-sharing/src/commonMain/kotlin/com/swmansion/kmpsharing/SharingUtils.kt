package com.swmansion.kmpsharing

/**
 * Returns information about the [ContentType] for the specified file.
 *
 * @param content The URL, raw text or path of the file.
 */
internal fun getContentType(content: String): ContentType {
    val trimmed = content.trim()

    return when {
        trimmed.startsWith("file://") -> ContentType.FILE
        trimmed.startsWith("http://") || trimmed.startsWith("https://") -> ContentType.LINK
        else -> ContentType.TEXT
    }
}

/**
 * Validates the sharing constraints for the given files.
 *
 * @param files The list of files to be shared.
 */
internal fun validateSharingConstraints(files: List<String>) {
    var fileCount = 0
    var urlCount = 0
    var textCount = 0

    files.forEach { file ->
        when (getContentType(file)) {
            ContentType.FILE -> fileCount++
            ContentType.LINK -> urlCount++
            ContentType.TEXT -> textCount++
        }
    }

    if (urlCount > 1) {
        throw IllegalArgumentException("Only one URL is allowed per share operation")
    }

    if (textCount > 1) {
        throw IllegalArgumentException("Only one text item is allowed per share operation")
    }

    if (urlCount > 0 && textCount > 0) {
        throw IllegalArgumentException("URL and text cannot be shared together")
    }
}
