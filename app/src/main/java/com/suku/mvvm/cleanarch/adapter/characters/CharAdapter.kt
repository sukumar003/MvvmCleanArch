package com.suku.mvvm.cleanarch.adapter.characters


import android.view.LayoutInflater
import android.view.ViewGroup
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.BaseRecyclerAdapter
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters

class CharAdapter : BaseRecyclerAdapter<Characters, CharViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharViewHolder {
        return CharViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_char,
                parent,
                false
            )
        )
    }
}