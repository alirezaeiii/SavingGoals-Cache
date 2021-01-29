package com.sample.android.qapital.util

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(val cause: String?) : Resource<T>()
}