package com.suku.mvvm.cleanarch.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suku.mvvm.cleanarch.data.local.database.dao.BooksDAO
import com.suku.mvvm.cleanarch.data.local.database.entity.Books

//@Database(entities = [(Movie::class), (Tv::class), (Person::class)], version = 3, exportSchema = false)
@Database(entities = [(Books::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDAO

    /*companion object {
        // prevents multiple instances of database opening at the same time.
        @Volatile
        private var instance: AppDatabase? = null
        val DB_NAME="books.db"

        fun getInstance(@ApplicationContext context:Context): AppDatabase? {
            if (instance == null) {
                //this mean this code called by one thread at a time
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "books.db"
                    ).build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }*/
}