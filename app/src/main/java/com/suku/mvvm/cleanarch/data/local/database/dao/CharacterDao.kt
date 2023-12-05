package com.suku.mvvm.cleanarch.data.local.database.dao

import androidx.room.*
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<Characters>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: Characters)

    @Query("SELECT * FROM characters WHERE bookCharId=:bookId")
    fun getAllItems(bookId: Long): List<Characters>

    @Query("SELECT charUrl FROM characters WHERE bookCharId=:bookId")
    fun getCharsUrl(bookId: Long): List<String>

    @Query("SELECT * FROM characters WHERE bookCharId=:bookId LIMIT 20 OFFSET:offSet")
    fun getCharList(bookId: Long, offSet: Int): List<Characters>
}