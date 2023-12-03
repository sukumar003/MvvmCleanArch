package com.suku.mvvm.cleanarch.data.repository

import com.suku.mvvm.cleanarch.data.dto.toBooks
import com.suku.mvvm.cleanarch.data.local.LocalDataSource
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//@Module
//Repositories will live same as the activity that requires them
//@InstallIn(ActivityComponent::class)
class BooksRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getBooks(): NetworkState<List<Books>> {
        return withContext(Dispatchers.IO) {
            if (localDataSource.getBooksCounts() == 0) {
                val serverResponse = remoteDataSource.getBooks().data
                if (serverResponse != null) {
                    localDataSource.insert(serverResponse.map { it.toBooks() })
                }
            }
            NetworkState.Success(localDataSource.getAllBooks())
        }
    }
}