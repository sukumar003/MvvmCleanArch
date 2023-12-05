package com.suku.mvvm.cleanarch.adapter.books


import android.view.View
import android.widget.TextView
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.BaseViewHolder
import com.suku.mvvm.cleanarch.data.local.database.entity.Books

class BookViewHolder(itemView: View, private val callback: ICallBackBook) :
    BaseViewHolder<Books>(itemView) {

    private val book: TextView = itemView.findViewById(R.id.book_name)
    override fun populateData() {
        book.text = data?.bookName
        itemView.setOnClickListener {
            callback.onClickItem(adapterPosition, data!!)
        }
    }
}