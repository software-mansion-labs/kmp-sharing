package com.swmansion.kmpsharing

public expect class Sharing {
    public companion object {
        public fun share(url: String, options: SharingOptions? = null)
    }
}
