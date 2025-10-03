package com.swmansion.kmpsharing.sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
