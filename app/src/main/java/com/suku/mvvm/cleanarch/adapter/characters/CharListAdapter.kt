package com.suku.mvvm.cleanarch.adapter.characters


import android.view.LayoutInflater
import android.view.ViewGroup
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.BaseRecyclerAdapter
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters


class CharListAdapter(private val callbackCharList: ICallbackCharList) :
    BaseRecyclerAdapter<Characters, CharListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharListViewHolder {
        return CharListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_char, parent, false),
            callbackCharList
        )
    }

    override fun onBindViewHolder(holder: CharListViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position == (itemCount - 2)) {
            callbackCharList.lastItem(position)
        }
    }
}