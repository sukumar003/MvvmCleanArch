package com.suku.mvvm.cleanarch.data.local.database.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Books(
    @PrimaryKey(autoGenerate = true)
    val booksId: Long,

    @ColumnInfo
    val bookName: String,

    @ColumnInfo
    val bookUrl: String,

    @ColumnInfo
    val charsUrl: ArrayList<String>
)