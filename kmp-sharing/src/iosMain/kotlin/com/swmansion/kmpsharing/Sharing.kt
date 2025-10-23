package com.swmansion.kmpsharing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.*
import platform.CoreGraphics.CGRectMake
import platform.Foundation.*
import platform.UIKit.*

/** Implementation of [rememberShare] function on iOS */
@Composable
@OptIn(ExperimentalForeignApi::class)
public actual fun rememberShare(): Share = remember {
    object : Share {
        override fun invoke(data: List<String>, options: SharingOptions?) {
            try {
                validateSharingConstraints(data)

                val activityItems =
                    data.map { file ->
                        when (getContentType(file)) {
                            DataType.FILE,
                            DataType.LINK -> {
                                val nsUrl = NSURL.URLWithString(file)
                                requireNotNull(nsUrl) { "Invalid URL: $file" }
                                nsUrl
                            }
                            else -> {
                                file
                            }
                        }
                    }

                val activityViewController =
                    UIActivityViewController(
                        activityItems = activityItems,
                        applicationActivities = null,
                    )

                val anchor =
                    options?.iosAnchor
                        ?: run {
                            val screenBounds = UIScreen.mainScreen.bounds
                            screenBounds.useContents {
                                val centerX = size.width / 2.0
                                val centerY = size.height / 2.0
                                Anchor(
                                    x = centerX.toFloat(),
                                    y = centerY.toFloat(),
                                    width = 200f,
                                    height = 50f,
                                )
                            }
                        }

                activityViewController.popoverPresentationController?.let { popover ->
                    popover.sourceView = UIApplication.sharedApplication.keyWindow
                    popover.sourceRect =
                        CGRectMake(
                            anchor.x.toDouble(),
                            anchor.y.toDouble(),
                            anchor.width.toDouble(),
                            anchor.height.toDouble(),
                        )
                }

                val rootViewController =
                    UIApplication.sharedApplication.keyWindow?.rootViewController
                requireNotNull(rootViewController) { "Could not find root view controller" }

                rootViewController.presentViewController(
                    activityViewController,
                    animated = true,
                    completion = null,
                )
            } catch (e: Exception) {
                throw RuntimeException("Failed to share: ${e.message}", e)
            }
        }
    }
}
