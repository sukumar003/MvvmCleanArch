package com.suku.mvvm.cleanarch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.data.remote.NetworkState
import com.suku.mvvm.cleanarch.databinding.ActivityDummyBinding
import com.suku.mvvm.cleanarch.util.NetworkUtils
import com.suku.mvvm.cleanarch.util.logd
import com.suku.mvvm.cleanarch.util.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DummyActivity : AppCompatActivity() {

    private val mainViewModel: DummyViewModel by viewModels()
    private lateinit var mainBinding: ActivityDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_dummy)
        observerDemoApiData()
        mainBinding.buttonNext.setOnClickListener {
            callDemoApi()
        }
    }

    private fun callDemoApi() {
        if (NetworkUtils.isNetWorkAvailable(applicationContext))
            mainViewModel.callApi()
        else
            showMessage(getString(R.string.no_internet))
    }

    private fun observerDemoApiData() {
        mainViewModel.liveDataEmployees.observe(this) { result ->

            when (result) {
                is NetworkState.Loading -> showProgressBar(true)

                is NetworkState.Success -> {
                    showProgressBar()
                    logd("RESULT DATA: ${Gson().toJson(result.data)}")
                    val bookResponse=result.data
                    if (bookResponse != null) {
                        for(item in bookResponse){
                            logd("OUTPUT: ${item.bookName}")
                        }
                    }
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

    private fun showProgressBar(isVisible: Boolean = false) {
        mainBinding.progressCircular.visibility = if (isVisible)
            View.VISIBLE
        else View.GONE
    }
}