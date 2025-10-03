package com.swmansion.kmpsharing

public class SharingFailedException(message: String, cause: Throwable? = null) :
    Exception(message, cause)

public class SharingInvalidArgumentException(message: String, cause: Throwable? = null) :
    Exception(message, cause)
