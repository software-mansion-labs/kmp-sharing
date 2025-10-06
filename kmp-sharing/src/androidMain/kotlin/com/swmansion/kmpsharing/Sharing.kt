package com.swmansion.kmpsharing

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import java.net.URLConnection

@Composable
@SuppressLint("ComposableNaming")
public actual fun share(url: String, options: SharingOptions?): () -> Unit {
    val context = LocalContext.current
    return remember {
        {
            try {
                val file = getLocalFileFromUrl(url)
                val contentUri =
                    FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

                val mimeType =
                    options?.androidMimeType
                        ?: URLConnection.guessContentTypeFromName(file.name)
                        ?: "*/*"

                val intent =
                    Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_STREAM, contentUri)
                        setTypeAndNormalize(mimeType)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                        options?.androidDialogTitle?.let { title ->
                            putExtra(Intent.EXTRA_TEXT, title)
                        }
                    }

                val chooser = Intent.createChooser(intent, options?.androidDialogTitle ?: "Share")
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                context.startActivity(chooser)
            } catch (e: Exception) {
                throw RuntimeException("Failed to share: ${e.message}", e)
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
