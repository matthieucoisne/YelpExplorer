package com.yelpexplorer.libraries.core.utils

sealed class Resource<out T> {
    data class Loading<T>(val data: T? = null): Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val errorMessage: String, val data: T? = null) : Resource<T>()
}
