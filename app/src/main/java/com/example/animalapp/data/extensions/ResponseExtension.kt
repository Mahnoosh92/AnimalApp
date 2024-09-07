package com.example.animalapp.data.utils.extensions

import com.example.animalapp.data.model.remote.ErrorDTO
import com.google.gson.Gson
import retrofit2.Response

fun Response<*>.getApiError(): ErrorDTO? {
    return try {
        val errorJsonString = this.errorBody()?.string()
        Gson().fromJson(errorJsonString, ErrorDTO::class.java)
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}