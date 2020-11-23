package com.sample.android.qapital.util

sealed class Resource<out T> {
    data class Loading<out T>(val isRefreshing: Boolean = false) : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(val cause: String?) : Resource<T>()
}