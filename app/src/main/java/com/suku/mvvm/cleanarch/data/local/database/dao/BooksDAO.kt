package com.suku.mvvm.cleanarch.data.local.database.dao

import androidx.room.*
import com.suku.mvvm.cleanarch.data.local.database.entity.Books

@Dao
interface BooksDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(books: List<Books>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(books: Books)

    @Query("SELECT COUNT(*) from books")
     fun booksCounts(): Int

    @Query("SELECT * FROM books")
     fun getAllItems(): List<Books>

    @Query("DELETE FROM books")
     fun deleteAll()

    @Update
     fun update(books: Books?)

    @Delete
     fun delete(books: Books?)
}