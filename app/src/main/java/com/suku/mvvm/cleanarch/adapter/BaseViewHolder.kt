package com.suku.mvvm.cleanarch.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(V: View) : RecyclerView.ViewHolder(V) {
    protected var TAG: String = javaClass.name
    var data: T? = null
    var currentItemPosition = -1

    fun bindData(data: T, position: Int) {
        this.data = data
        this.currentItemPosition = position
        populateData()
    }

    abstract fun populateData()
}