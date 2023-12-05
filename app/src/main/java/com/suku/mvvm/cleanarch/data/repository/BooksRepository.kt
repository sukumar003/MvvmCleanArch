package com.suku.mvvm.cleanarch.data.repository

import com.suku.mvvm.cleanarch.data.dto.toBook
import com.suku.mvvm.cleanarch.data.dto.toCharacter
import com.suku.mvvm.cleanarch.data.local.LocalDataSource
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getBooks(): NetworkState<List<Books>> {
        return withContext(Dispatchers.IO) {
            if (localDataSource.getBooksCounts() == 0) {
                val serverResponse = remoteDataSource.getBooks().data
                if (serverResponse != null) {
                    localDataSource.insert(serverResponse.map { it.toBook() })
                }
            }
            NetworkState.Success(localDataSource.getAllBooks())
        }
    }

    private suspend fun getCharsUrl(bookId: Long): List<String> {
        return withContext(Dispatchers.IO) {
            localDataSource.getCharUrl(bookId)
        }
    }

    suspend fun callCharApi(book: Books): NetworkState<List<Characters>> {
        // TODO pagination
        return withContext(Dispatchers.IO) {
            val bookId = book.booksId
            // get the existing URl list to compare and avoid second time api call.
            val existingUrl = getCharsUrl(bookId)

            val minimumRange = 0
            val currentPosition = minimumRange + 10
            val totalCharSize = book.charsUrl.size
            val takenList = if (currentPosition <= totalCharSize - 1) {
                book.charsUrl.slice(minimumRange..currentPosition)
            } else {
                book.charsUrl.slice(minimumRange..<totalCharSize)
            }
            takenList.forEach {
                if (!existingUrl.contains(it)) {
                    // call api and get the result
                    val response = remoteDataSource.getCharsList(it).data
                    // store into DB
                    localDataSource.insertChars(response?.toCharacter(bookId)!!)
                }
            }

            // get the chars data from DB notify to UI.
            NetworkState.Success(localDataSource.getCharList(bookId, minimumRange))
        }
    }
}