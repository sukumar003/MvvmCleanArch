package com.suku.mvvm.cleanarch.data.remote

import com.suku.mvvm.cleanarch.data.dto.BooksResponse
import com.suku.mvvm.cleanarch.data.dto.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("books")
    suspend fun getBooks(): Response<List<BooksResponse>>

    @GET
    suspend fun getCharList(@Url url: String): Response<CharacterResponse>
}