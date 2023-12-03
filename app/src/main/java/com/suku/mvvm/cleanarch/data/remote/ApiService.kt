package com.suku.mvvm.cleanarch.data.remote

import com.suku.mvvm.cleanarch.data.dto.BooksResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("books")
    suspend fun getBooks(): Response<List<BooksResponse>>


    /*@GET("books")
    suspend fun getEmployeesAsync(@Query("q") query: String): Response<BooksResponse>*/
}