package com.suku.mvvm.cleanarch.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suku.mvvm.cleanarch.data.local.database.dao.BookDao
import com.suku.mvvm.cleanarch.data.local.database.dao.CharacterDao
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.util.ArrayListConverter

@Database(entities = [(Books::class), (Characters::class)], version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BookDao
    abstract fun charsDao(): CharacterDao
}