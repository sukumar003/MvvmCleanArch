package com.suku.mvvm.cleanarch.data.dto

import com.suku.mvvm.cleanarch.data.local.database.entity.Books

data class BooksResponse(val url: String, val name: String, val characters: ArrayList<String>)

fun BooksResponse.toBook() = Books(
    booksId = 0,
    bookName = name,
    bookUrl = url,
    charsUrl = characters
)