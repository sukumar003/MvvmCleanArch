package com.suku.mvvm.cleanarch.adapter.characters

import com.suku.mvvm.cleanarch.data.local.database.entity.Characters

interface ICallbackCharList {
    fun onClickItem(characters: Characters?)
    fun lastItem(lastItemPosition: Int)
}