package com.suku.mvvm.cleanarch.data.remote

sealed class NetworkState<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : NetworkState<T>(data)

    class Failure<T>(message: String?) : NetworkState<T>(null, message)

    class Loading<T>(message: String?=null) : NetworkState<T>(null, message)
}