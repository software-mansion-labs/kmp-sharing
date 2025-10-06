package com.swmansion.kmpsharing

import androidx.compose.runtime.Composable

public interface Share {
    public operator fun invoke(url: String, options: SharingOptions? = null)
}

@Composable public expect fun rememberShare(): Share
