package com.suku.mvvm.cleanarch.data.repository

import com.suku.mvvm.cleanarch.data.dto.toBook
import com.suku.mvvm.cleanarch.data.dto.toCharacter
import com.suku.mvvm.cleanarch.data.local.LocalDataSource
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.data.remote.RemoteDataSource
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class BooksRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getBooks(): NetworkState<List<Books>> {
        return withContext(Dispatchers.IO) {
            val serverResponse = remoteDataSource.getBooks().data
            if (!serverResponse.isNullOrEmpty()) {
                NetworkState.Success(serverResponse.map { it.toBook() }.toList())
            } else NetworkState.Failure("Unknown exception. Try again later")
        }
    }

    suspend fun getCharacter(charUrl: String): NetworkState<Characters> {
        return withContext(Dispatchers.IO) {
            val response: Characters
            if (localDataSource.charUrlExists(charUrl)) {
                response = localDataSource.getCharacter(charUrl)
            } else {
                response = remoteDataSource.getCharacterDetails(charUrl).data?.toCharacter()!!
                localDataSource.insertCharacter(response)
            }
            NetworkState.Success(response)
        }
    }


}