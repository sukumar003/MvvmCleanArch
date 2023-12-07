package com.suku.mvvm.cleanarch.adapter.books

import com.suku.mvvm.cleanarch.data.local.database.entity.Books

interface ICallBackBook {
    fun onClickItem(book: Books)
}