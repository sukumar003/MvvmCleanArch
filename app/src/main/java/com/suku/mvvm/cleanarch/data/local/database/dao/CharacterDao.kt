package com.suku.mvvm.cleanarch.data.local.database.dao

import androidx.room.*
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters

@Dao
interface CharacterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: Characters)


    @Query("SELECT * FROM characters WHERE charUrl=:url")
    fun getCharacterData(url: String): Characters


    @Query("SELECT EXISTS (SELECT 1 FROM characters WHERE charUrl = :url)")
    fun charUrlExists(url: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<Characters>)

    @Update
    fun updateCharacter(char: Characters)
}