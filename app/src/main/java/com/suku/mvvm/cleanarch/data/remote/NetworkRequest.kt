package com.suku.mvvm.cleanarch.data.remote

import retrofit2.Response

open class NetworkRequest {
    suspend fun <T> invokeApiRequest(
        apiCall: suspend () -> Response<T>
    ): NetworkState<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkState.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkState<T> =
        NetworkState.Failure(errorMessage)
}