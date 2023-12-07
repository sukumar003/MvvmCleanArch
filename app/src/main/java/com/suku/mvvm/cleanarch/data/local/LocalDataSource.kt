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

    // books
    fun getAllBooks(): List<Books> {
        return bookDao.getAllItems()
    }

    fun getBooksCounts(): Int {
        return bookDao.booksCounts()
    }

    fun insert(books: List<Books>) {
        bookDao.insert(books)
    }

    // characters
    fun insertCharacter(characters: Characters) {
        characterDao.insert(characters)
    }

    fun charUrlExists(url: String): Boolean {
        return characterDao.charUrlExists(url)
    }

    fun getCharacter(url: String): Characters {
        return characterDao.getCharacterData(url)
    }
}