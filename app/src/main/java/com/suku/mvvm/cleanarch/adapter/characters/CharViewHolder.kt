package com.suku.mvvm.cleanarch.adapter.characters


import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.suku.mvvm.cleanarch.R
import com.suku.mvvm.cleanarch.adapter.BaseViewHolder
import com.suku.mvvm.cleanarch.data.local.database.entity.Characters

class CharViewHolder(itemView: View) :
    BaseViewHolder<Characters>(itemView) {

    private val name: TextView = itemView.findViewById(R.id.char_name)
    private val gender: TextView = itemView.findViewById(R.id.char_gender)
    private val title: TextView = itemView.findViewById(R.id.char_title)
    private val alias: TextView = itemView.findViewById(R.id.char_alias)

    @SuppressLint("SetTextI18n")
    override fun populateData() {
        if (!data?.charName.isNullOrEmpty()) {
            name.visibility = View.VISIBLE
            name.text = "Name: ${data?.charName}"
        }
        if (!data?.charGender.isNullOrEmpty()) {
            gender.visibility = View.VISIBLE
            gender.text = "Gender: ${data?.charGender}"
        }

        if (data?.titles?.size!! > 0) {
            if (!data?.titles?.get(0).isNullOrEmpty()) {
                title.visibility = View.VISIBLE
                title.text = "Title: ${data?.titles?.get(0)}"
            }
        }

        if (data?.alias?.size!! > 0) {
            if (!data?.alias?.get(0).isNullOrEmpty()) {
                alias.visibility = View.VISIBLE
                alias.text = "Alias: ${data?.alias?.get(0)}"
            }
        }
    }
}