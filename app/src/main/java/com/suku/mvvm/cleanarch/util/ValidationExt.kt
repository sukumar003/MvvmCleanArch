package com.suku.mvvm.cleanarch.util

fun isNullOrEmpty(value: String?): Boolean =
    value == null || value.trim() == "" || value.isEmpty() || value.equals(
        "null",
        ignoreCase = true
    )



