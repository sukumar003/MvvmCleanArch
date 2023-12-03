package com.suku.mvvm.cleanarch.data.local

import com.suku.mvvm.cleanarch.data.local.database.dao.BooksDAO
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val booksDAO: BooksDAO) {

    fun getAllBooks(): List<Books> {
        return booksDAO.getAllItems()
    }

    fun getBooksCounts(): Int {
        return booksDAO.booksCounts()
    }

    fun insert(books: List<Books>) {
        booksDAO.insert(books)
    }
}