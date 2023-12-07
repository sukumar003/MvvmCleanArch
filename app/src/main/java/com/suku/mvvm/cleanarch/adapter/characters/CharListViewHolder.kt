package com.suku.mvvm.cleanarch.adapter.characters


import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.BaseViewHolder
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters


class CharListViewHolder(itemView: View, private val callbackCharList: ICallbackCharList) :
    BaseViewHolder<Characters>(itemView) {

    private val item: TextView = itemView.findViewById(R.id.item)
    private val name: TextView = itemView.findViewById(R.id.char_name)
    private val gender: TextView = itemView.findViewById(R.id.char_gender)
    private val title: TextView = itemView.findViewById(R.id.char_title)
    private val alias: TextView = itemView.findViewById(R.id.char_alias)

    @SuppressLint("SetTextI18n")
    override fun populateData() {
        item.text = "ITEM:$currentItemPosition"
        val response = data!!
        if (response.charName.isNotEmpty()) {
            name.visibility = View.VISIBLE
            name.text = "Name: ${response.charName}"
        }
        if (response.charGender.isNotEmpty()) {
            gender.visibility = View.VISIBLE
            gender.text = "Gender: ${response.charGender}"
        }

        if (response.titles.size > 0) {
            if (response.titles[0].isNotEmpty()) {
                title.visibility = View.VISIBLE
                title.text = "Title: ${response.titles[0]}"
            }
        }

        if (response.alias.size > 0) {
            if (response.alias[0].isNotEmpty()) {
                alias.visibility = View.VISIBLE
                alias.text = "Alias: ${response.alias[0]}"
            }
        }
        itemView.setOnClickListener {
            callbackCharList.onClickItem(data)
        }
    }
}