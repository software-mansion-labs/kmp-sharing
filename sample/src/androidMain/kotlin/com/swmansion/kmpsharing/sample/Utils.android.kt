package com.swmansion.kmpsharing.sample


import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.createBitmap
import java.io.OutputStream
import kotlin.random.Random

@Composable
actual fun createAndSaveTestBitmap(): String? {
    // 1. Create a Bitmap with random colors
    val width = 512
    val height = 512
    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    // Fill background with a random color
    paint.color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

    // Draw some random circles
    repeat(5) {
        paint.color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        val radius = Random.nextInt(20, 100).toFloat()
        val cx = Random.nextInt(radius.toInt(), width - radius.toInt()).toFloat()
        val cy = Random.nextInt(radius.toInt(), height - radius.toInt()).toFloat()
        canvas.drawCircle(cx, cy, radius, paint)
    }

    // 2. Save to MediaStore
    val displayName = "test_image_${System.currentTimeMillis()}"
    val imageCollection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, bitmap.width)
        put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }

    val contentResolver = LocalContext.current.contentResolver
    val uri: Uri? = contentResolver.insert(imageCollection, contentValues)

    uri?.let {
        contentResolver.openOutputStream(it)?.use { outputStream: OutputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            contentResolver.update(uri, contentValues, null, null)
        }
    }

    return uri.toString()
}
