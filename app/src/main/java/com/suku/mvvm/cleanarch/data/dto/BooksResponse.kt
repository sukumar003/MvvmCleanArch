package com.suku.mvvm.cleanarch.data.dto

import com.suku.mvvm.cleanarch.data.local.database.entity.Books

data class BooksResponse(val url:String, val name:String)

fun BooksResponse.toBooks() = Books(
    booksId = 0,
    bookName = name,
    bookUrl = url
)