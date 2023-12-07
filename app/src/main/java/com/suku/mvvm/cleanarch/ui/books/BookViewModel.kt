package com.suku.mvvm.cleanarch.ui.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {

    var liveDataBooks = MutableLiveData<NetworkState<List<Books>>>()
    var liveDataChars = MutableLiveData<NetworkState<List<Characters>>>()

    fun callBookApi() {
        viewModelScope.launch {
            liveDataBooks.postValue(NetworkState.Loading())
            val result = repository.getBooks()
            liveDataBooks.postValue(result)
        }
    }

    suspend fun callCharacterApi(charsUrl: List<String>) {
        liveDataChars.postValue(NetworkState.Loading())
        val tempCharList = ArrayList<Characters>()
        viewModelScope.async {
            charsUrl.forEach {
                val response = repository.getCharacter(it)
                if (response.data != null)
                    tempCharList.add(response.data)
            }
        }.await()
        liveDataChars.postValue(NetworkState.Success(tempCharList))
    }
}