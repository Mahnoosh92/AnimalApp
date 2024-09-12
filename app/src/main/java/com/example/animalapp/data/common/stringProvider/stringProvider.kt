package com.example.animalapp.data.common.stringProvider

import androidx.annotation.StringRes

interface StringProvider {

    fun getString(@StringRes id: Int): String
}