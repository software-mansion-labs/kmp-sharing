package com.swmansion.kmpsharing.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.swmansion.kmpsharing.Sharing
import com.swmansion.kmpsharing.SharingOptions
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier =
                Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    Sharing.share(
                        // Remember: URL must start with "file://" and point to a file that exists
                        // on the device
                        url = "file:///data/data/com.swmansion.kmpsharing.sample/files/dog.jpg",
                        options =
                            SharingOptions(
                                androidDialogTitle = "Share Dog Image",
                                androidMimeType = "image/jpeg",
                            ),
                    )
                }
            ) {
                Text("Share a file!")
            }
        }
    }
}
