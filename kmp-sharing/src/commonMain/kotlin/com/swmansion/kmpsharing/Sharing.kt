package com.swmansion.kmpsharing

import androidx.compose.runtime.Composable

@Composable public expect fun share(url: String, options: SharingOptions?): () -> Unit
