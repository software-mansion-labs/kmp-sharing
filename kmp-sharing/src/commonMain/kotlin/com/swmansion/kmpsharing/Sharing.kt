package com.swmansion.kmpsharing

import androidx.compose.runtime.Composable

@Composable public expect fun rememberShare(): (url: String, options: SharingOptions?) -> Unit
