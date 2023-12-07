package com.suku.mvvm.cleanarch.di

import android.content.Context
import androidx.room.Room
import com.suku.mvvm.cleanarch.data.local.database.AppDatabase
import com.suku.mvvm.cleanarch.data.local.database.dao.BookDao
import com.suku.mvvm.cleanarch.data.local.database.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideBooksDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.booksDao()
    }

    @Provides
    fun provideCharsDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.charsDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "books.db"
        ).build()
    }
}