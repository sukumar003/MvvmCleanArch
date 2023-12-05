package com.suku.mvvm.cleanarch.data.local

import com.suku.mvvm.cleanarch.data.local.database.dao.BookDao
import com.suku.mvvm.cleanarch.data.local.database.dao.CharacterDao
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val bookDao: BookDao,
    private val characterDao: CharacterDao
) {

    fun getAllBooks(): List<Books> {
        return bookDao.getAllItems()
    }

    fun getBooksCounts(): Int {
        return bookDao.booksCounts()
    }

    fun insert(books: List<Books>) {
        bookDao.insert(books)
    }

    fun insertChars(characters: Characters) {
        characterDao.insert(characters)
    }

    fun getCharUrl(bookId: Long): List<String> {
        return characterDao.getCharsUrl(bookId)
    }

    fun getAllChars(bookId: Long): List<Characters> {
        return characterDao.getAllItems(bookId)
    }

    fun getCharList(bookId: Long, offSet: Int): List<Characters> {
        return characterDao.getCharList(bookId, offSet)
    }
}