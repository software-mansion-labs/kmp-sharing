package com.swmansion.kmpsharing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File
import java.net.URLConnection

public actual fun share(url: String, options: SharingOptions?) {
    val context = getContext()

    try {
        val file = getLocalFileFromUrl(url)

        val contentUri =
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

        val mimeType =
            options?.androidMimeType ?: URLConnection.guessContentTypeFromName(file.name) ?: "*/*"

        val intent =
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, contentUri)
                setTypeAndNormalize(mimeType)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                options?.androidDialogTitle?.let { title -> putExtra(Intent.EXTRA_TEXT, title) }
            }

        val chooser = Intent.createChooser(intent, options?.androidDialogTitle ?: "Share")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(chooser)
    } catch (e: Exception) {
        throw SharingFailedException("Failed to share: ${e.message}", e)
    }
}

@SuppressLint("PrivateApi")
private fun getContext(): Context {
    return try {
        val applicationClass = Class.forName("android.app.ActivityThread")
        val currentApplication =
            applicationClass.getMethod("currentApplication").invoke(null) as? Context

        if (currentApplication != null) {
            currentApplication
        } else {
            val activityThread = applicationClass.getMethod("currentActivityThread").invoke(null)
            val app =
                activityThread?.javaClass?.getMethod("getApplication")?.invoke(activityThread)
                    as? Context

            app ?: throw IllegalStateException("Could not get application context")
        }
    } catch (e: Exception) {
        throw IllegalStateException("Could not get Android context automatically.", e)
    }
}

@Throws(SharingInvalidArgumentException::class)
private fun getLocalFileFromUrl(url: String?): File {
    if (url == null) {
        throw SharingInvalidArgumentException("URL to share cannot be null.")
    }

    val uri = url.toUri()
    if ("file" != uri.scheme) {
        throw SharingInvalidArgumentException(
            "Only local file URLs are supported (expected scheme to be 'file', got '${uri.scheme}')."
        )
    }

    val path =
        uri.path
            ?: throw SharingInvalidArgumentException(
                "Path component of the URL to share cannot be null."
            )

    val file = File(path)
    if (!file.exists()) {
        throw SharingInvalidArgumentException("File does not exist: $path")
    }

    return file
}
