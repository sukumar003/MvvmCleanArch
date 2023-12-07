package com.suku.mvvm.cleanarch.ui.books

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.books.BookAdapter
import com.suku.mvvm.cleanarch.adapter.books.ICallBackBook
import com.suku.mvvm.cleanarch.adapter.characters.CharListAdapter
import com.suku.mvvm.cleanarch.adapter.characters.ICallbackCharList
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.databinding.ActivityBookBinding
import com.suku.mvvm.cleanarch.util.NetworkUtils
import com.suku.mvvm.cleanarch.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BookActivity : AppCompatActivity() {


    private lateinit var binding: ActivityBookBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bookItemView: RecyclerView
    private lateinit var charItemView: RecyclerView
    private lateinit var infoText: TextView
    private val bookViewModel: BookViewModel by viewModels()
    private var charListAdapter: CharListAdapter? = null

    lateinit var charactersUrlList: ArrayList<String>
    var globalInitialCount = 0
    var globalPatchCount = 0
    var charactersURLSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.drawerLayout
        setupDrawer()
        bookItemView = binding.customNavigationLayout.navBody.bookListView
        charItemView = binding.appBarMain.contentMain.charListView
        infoText = binding.appBarMain.contentMain.infoHomePage
        observerBooks()
        observerCharList()
        callBookApi()
    }

    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showProgressBar(isVisible: Boolean = false) {
        findViewById<ProgressBar>(R.id.progressCircular).visibility =
            if (isVisible)
                View.VISIBLE
            else View.GONE
    }

    private fun callBookApi() {
        if (NetworkUtils.isNetWorkAvailable(applicationContext))
            bookViewModel.callBookApi()
        else
            showMessage(getString(R.string.no_internet))
    }

    private fun observerBooks() {
        bookViewModel.liveDataBooks.observe(this) { result ->
            when (result) {
                is NetworkState.Loading -> showProgressBar(true)

                is NetworkState.Success -> {
                    showProgressBar()
                    setBooksAdapter(result.data)
                }

                is NetworkState.Failure -> {
                    showProgressBar()
                    showMessage("${result.message}")
                }

                else -> {
                    showProgressBar()
                    showMessage("${result.message}")
                }
            }
        }
    }

    private fun setBooksAdapter(data: List<Books>?) {
        val bookAdapter = BookAdapter(callbackBook)
        bookItemView.visibility = View.VISIBLE
        bookItemView.adapter = bookAdapter
        bookAdapter.addItems(data?.toMutableList())
    }

    private val callbackBook = object : ICallBackBook {
        override fun onClickItem(book: Books) {
            closeDrawer()
            updateTitle(book)
            charactersUrlList = ArrayList()
            charactersUrlList.addAll(book.charsUrl)
            charactersURLSize = charactersUrlList.size - 1
            globalInitialCount = 0
            globalPatchCount = 20
            initializeAdapter()
            patchValidation(globalInitialCount, globalPatchCount)
        }
    }

    private fun updateTitle(book: Books) {
        binding.appBarMain.toolbar.title = book.bookName
    }

    private fun initializeAdapter() {
        charListAdapter = CharListAdapter(callbackCharList)
        charItemView.adapter = charListAdapter
    }

    private fun patchValidation(initialCount: Int, patchCount: Int) {
        if (patchCount <= charactersURLSize) {
            triggerCharacterApi(charactersUrlList.slice(initialCount..patchCount))
        } else {
            globalPatchCount = charactersURLSize
            triggerCharacterApi(charactersUrlList.slice(initialCount..globalPatchCount))
        }
    }


    private fun triggerCharacterApi(sliceData: List<String>) {
        bookViewModel.viewModelScope.launch {
            if (charactersUrlList.size == charListAdapter?.itemCount) {
                // DO nothing
            } else {
                bookViewModel.callCharacterApi(sliceData)
            }
        }
    }


    private fun observerCharList() {
        bookViewModel.liveDataChars.observe(this) { result ->
            when (result) {
                is NetworkState.Loading -> showProgressBar(true)

                is NetworkState.Success -> {
                    showProgressBar()
                    updateCharacterListAdapter(result.data)
                }

                is NetworkState.Failure -> {
                    showProgressBar()
                    showMessage("${result.message}")
                }

                else -> {
                    showProgressBar()
                    showMessage("${result.message}")
                }
            }
        }
    }


    private fun updateCharacterListAdapter(data: List<Characters>?) {
        infoText.visibility = View.GONE
        charListAdapter?.addItems(data?.toMutableList())
    }

    private val callbackCharList = object : ICallbackCharList {
        override fun onClickItem(characters: Characters?) {
            showMessage("onClicked: \n" + Gson().toJson(characters))
        }

        override fun lastItem(lastItemPosition: Int) {
            globalInitialCount = globalPatchCount + 1
            globalPatchCount += 14
            patchValidation(globalInitialCount, globalPatchCount)
        }
    }
}