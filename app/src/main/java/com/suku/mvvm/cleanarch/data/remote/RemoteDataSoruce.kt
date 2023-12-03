package com.suku.mvvm.cleanarch.data.remote

import com.suku.mvvm.cleanarch.data.dto.BooksResponse

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : NetworkRequest() {

    suspend fun getBooks(): NetworkState<List<BooksResponse>> {
        return invokeApiRequest(apiCall = {
            apiService.getBooks()
        })
    }
}