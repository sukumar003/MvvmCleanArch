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
import androidx.recyclerview.widget.RecyclerView
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.books.BookAdapter
import com.suku.mvvm.cleanarch.adapter.books.ICallBackBook
import com.suku.mvvm.cleanarch.adapter.characters.CharAdapter
import com.suku.mvvm.cleanarch.data.local.database.entity.Books
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.databinding.ActivityBookBinding
import com.suku.mvvm.cleanarch.util.NetworkUtils
import com.suku.mvvm.cleanarch.util.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookActivity : AppCompatActivity() {


    private lateinit var binding: ActivityBookBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bookItemView: RecyclerView
    private lateinit var charItemView: RecyclerView
    private lateinit var infoText: TextView
    private val bookViewModel: BookViewModel by viewModels()

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
        observerChars()
        callBookApi()
    }

    private fun callBookApi() {
        if (NetworkUtils.isNetWorkAvailable(applicationContext))
            bookViewModel.callBookApi()
        else
            showMessage(getString(R.string.no_internet))
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
        override fun onClickItem(adapterPosition: Int, book: Books) {
            closeDrawer()
            bookViewModel.callCharactersApi(book)
        }
    }

    private fun observerChars() {
        bookViewModel.liveDataChars.observe(this) { result ->
            when (result) {
                is NetworkState.Loading -> showProgressBar(true)

                is NetworkState.Success -> {
                    showProgressBar()
                    setCharsAdapter(result.data)
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

    private fun setCharsAdapter(data: List<Characters>?) {
        // TODO pagination
        infoText.visibility = View.GONE
        val charAdapter = CharAdapter()
        charItemView.adapter = charAdapter
        charAdapter.addItems(data?.toMutableList())
    }
}