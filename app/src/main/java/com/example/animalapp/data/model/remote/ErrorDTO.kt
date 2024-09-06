package com.example.animalapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class ErrorDTO(@SerializedName("message") val message: String)
