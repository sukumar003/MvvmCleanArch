package com.suku.mvvm.cleanarch.di

import android.content.Context
import androidx.room.Room
import com.suku.mvvm.cleanarch.data.local.database.AppDatabase
import com.suku.mvvm.cleanarch.data.local.database.dao.BooksDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideBooksDao(appDatabase: AppDatabase): BooksDAO {
        return appDatabase.booksDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        //this mean this code called by one thread at a time
        return synchronized(this) {
            Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "books.db"
            ).build()
        }

    }
}