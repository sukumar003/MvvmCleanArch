package com.suku.mvvm.cleanarch.data.repository

import com.suku.mvvm.cleanarch.data.dto.BooksResponse
import com.suku.mvvm.cleanarch.data.dto.CharacterResponse
import com.suku.mvvm.cleanarch.data.dto.toBook
import com.suku.mvvm.cleanarch.data.dto.toCharacter
import com.suku.mvvm.cleanarch.data.local.LocalDataSource
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.data.remote.ApiService
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.data.remote.RemoteDataSource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BooksRepositoryTest {

    private val booksRepository = mockk<BooksRepository>(relaxed = true)
    private val localDataSource = mockk<LocalDataSource>(relaxed = true)
    private val remoteDataSource = mockk<RemoteDataSource>(relaxed = true)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun onGetBooks() {
        runBlocking {
            val list = ArrayList<Books>()
            list.add(mockk<Books>())
            // mock
            coEvery { booksRepository.getBooks() } returns NetworkState.Success(list)
            // target
            val result = booksRepository.getBooks()
            // verify
            coVerify {
                booksRepository.getBooks()
            }
            // assert
            if (result is NetworkState.Success)
                assertEquals(result.data?.size, 1)
            else
                assert(true)
        }
    }

    @Test
    fun onGetBooksFromAPi() {
        runBlocking {
            val list = ArrayList<BooksResponse>()
            list.add(mockk<BooksResponse>(relaxed = true))
            list.map { it.toBook() }.toList()

            coEvery { remoteDataSource.getBooks() } returns NetworkState.Success(list)

            val result = booksRepository.getBooks()

            coVerify(exactly = 1) { remoteDataSource.getBooks() }

            if (result is NetworkState.Success)
                assertEquals(result.data, list.map { it.toBook() }.toList())
            else
                assert(true)
        }
    }

    @Test
    fun onGetCharacterWhenURLExists() {
        val characters = mockk<Characters>(relaxed = true)
        runBlocking {
            // mock
            //every { localDataSource.charUrlExists(any()) } returns true
            coEvery { localDataSource.getCharacter(any()) } returns characters
            // target api
            val result = booksRepository.getCharacter("URL")
            if (result is NetworkState.Success) {
                assertEquals(result.data, characters)
            } else
                assert(true)
        }
    }

    @Test
    fun onGetCharacterWhenURLDoesNotExists() {
        val characterResponse = mockk<CharacterResponse>(relaxed = true)
        characterResponse.toCharacter()
        runBlocking {
            // mock
            every { localDataSource.charUrlExists(any()) } returns false
            coEvery { remoteDataSource.getCharacterDetails(any()) } returns NetworkState.Success(
                characterResponse
            )
            // target api
            val result = booksRepository.getCharacter("URL")
            // assert that
            if (result is NetworkState.Success) {
                assertEquals(result.data, characterResponse.toCharacter())
            } else
                assert(true)
        }
    }

    @Test
    fun toVerifyCharacterInsertingToDB() {
        val characters = mockk<Characters>(relaxed = true)
        val characterResponse = mockk<CharacterResponse>(relaxed = true)
        characterResponse.toCharacter()
        runBlocking {
            // mock
            every { localDataSource.charUrlExists(any()) } returns false
            coEvery { remoteDataSource.getCharacterDetails(any()) } returns NetworkState.Success(
                characterResponse
            )
            // target api
            val result = booksRepository.getCharacter("URL")
            // verify
            verify { localDataSource.insertCharacter(result.data!!) }
        }
    }
}