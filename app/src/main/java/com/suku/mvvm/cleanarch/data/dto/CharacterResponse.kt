package com.suku.mvvm.cleanarch.data.dto

import com.suku.mvvm.cleanarch.data.local.database.entity.Characters

data class CharacterResponse(
    val url: String,
    val name: String,
    val gender: String,
    val titles: ArrayList<String>,
    val aliases: ArrayList<String>
)

fun CharacterResponse.toCharacter(id: Long) = Characters(
    charId = 0,
    charUrl = url,
    charName = name,
    charGender = gender,
    titles = titles,
    alias = aliases,
    bookCharId = id
)