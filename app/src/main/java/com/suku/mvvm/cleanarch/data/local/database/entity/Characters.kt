package com.suku.mvvm.cleanarch.data.local.database.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Characters(
    @PrimaryKey(autoGenerate = true)
    val charId: Long,

    @ColumnInfo
    val charUrl: String,

    @ColumnInfo
    val charName: String,

    @ColumnInfo
    val charGender: String,

    @ColumnInfo
    val titles: ArrayList<String>,

    @ColumnInfo
    val alias: ArrayList<String>
)