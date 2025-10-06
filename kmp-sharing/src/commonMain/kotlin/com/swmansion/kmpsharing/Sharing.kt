package com.swmansion.kmpsharing

import androidx.compose.runtime.Composable

@Composable public expect fun Share(url: String, options: SharingOptions?): () -> Unit
