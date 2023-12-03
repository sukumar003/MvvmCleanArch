package com.suku.mvvm.cleanarch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {

    var liveDataEmployees = MutableLiveData<NetworkState<List<Books>>>()

    fun callApi() {
        viewModelScope.launch {
            liveDataEmployees.postValue(NetworkState.Loading())
            val result = repository.getBooks()
            liveDataEmployees.postValue(result)
        }
    }
}