package com.swmansion.kmpsharing

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File

/** Implementation of [rememberShare] function on Android */
@Composable
public actual fun rememberShare(): Share {
    val context = LocalContext.current
    return remember {
        object : Share {
            override fun invoke(data: List<String>, options: SharingOptions?) {
                try {
                    validateSharingConstraints(data)

                    val contentUris = mutableListOf<android.net.Uri>()
                    val textItems = mutableListOf<String>()

                    data.forEach { file ->
                        when (getContentType(file)) {
                            ContentType.FILE -> {
                                val fileObj = getLocalFileFromUrl(file)
                                val contentUri =
                                    FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        fileObj,
                                    )
                                contentUris.add(contentUri)
                            }
                            ContentType.CONTENT -> {
                                contentUris.add(file.toUri())
                            }
                            ContentType.LINK,
                            ContentType.TEXT -> {
                                textItems.add(file)
                            }
                        }
                    }

                    val intent =
                        Intent(
                            if (contentUris.size > 1) Intent.ACTION_SEND_MULTIPLE
                            else Intent.ACTION_SEND
                        )

                    if (contentUris.isNotEmpty()) {
                        if (contentUris.size == 1) {
                            intent.putExtra(Intent.EXTRA_STREAM, contentUris[0])
                        } else {
                            intent.putParcelableArrayListExtra(
                                Intent.EXTRA_STREAM,
                                ArrayList(contentUris),
                            )
                        }

                        val mimeType = options?.androidMimeType ?: "image/*"
                        intent.setTypeAndNormalize(mimeType)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    } else {
                        intent.setTypeAndNormalize("text/plain")
                    }

                    if (textItems.isNotEmpty()) {
                        intent.putExtra(Intent.EXTRA_TEXT, textItems.joinToString("\n"))
                    }

                    options?.androidDialogTitle?.let { title ->
                        intent.putExtra(Intent.EXTRA_TITLE, title)
                    }

                    context.startActivity(
                        Intent.createChooser(intent, options?.androidDialogTitle ?: "Share")
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                } catch (e: Exception) {
                    throw RuntimeException("Failed to share: ${e.message}", e)
                }
            }
        }
    }
}

private fun getLocalFileFromUrl(url: String): File {
    val uri = url.toUri()
    require(uri.scheme == "file") {
        "Only local file URLs are supported (expected scheme to be 'file', got '${uri.scheme}')."
    }

    val path = uri.path
    requireNotNull(path) { "Path component of the URL to share cannot be null." }

    val file = File(path)
    require(file.exists()) { "File does not exist: $path" }

    return file
}
