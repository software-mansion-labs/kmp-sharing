package com.swmansion.kmpsharing

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.UIKit.*

@OptIn(ExperimentalForeignApi::class)
public actual fun share(url: String, options: SharingOptions?) {
    try {
        val nsUrl = NSURL.URLWithString(url)
        requireNotNull(nsUrl) { "Invalid URL: $url" }

        val activityItems = listOf(nsUrl)
        val activityViewController =
            UIActivityViewController(activityItems = activityItems, applicationActivities = null)

        options?.iosAnchor?.let { anchor ->
            activityViewController.popoverPresentationController?.let { popover ->
                popover.sourceView = UIApplication.sharedApplication.keyWindow
                popover.sourceRect =
                    platform.CoreGraphics.CGRectMake(
                        anchor.x.toDouble(),
                        anchor.y.toDouble(),
                        anchor.width.toDouble(),
                        anchor.height.toDouble(),
                    )
            }
        }

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
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
