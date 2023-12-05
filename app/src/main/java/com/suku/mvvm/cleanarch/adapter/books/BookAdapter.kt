package com.suku.mvvm.cleanarch.adapter.books


import android.view.LayoutInflater
import android.view.ViewGroup
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.BaseRecyclerAdapter
import com.suku.mvvm.cleanarch.data.local.database.entity.Books

class BookAdapter(private val callback: ICallBackBook) :
    BaseRecyclerAdapter<Books, BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_books,
                parent,
                false
            ),
            callback
        )
    }
}