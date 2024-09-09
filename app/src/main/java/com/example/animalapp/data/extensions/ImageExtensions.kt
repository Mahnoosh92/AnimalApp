package com.example.animalapp.data.extensions

import com.example.animalapp.domain.model.Breed

const val imagePrefix = "https://cdn2.thecatapi.com/images/"

fun Breed.extractName(): String? = this.imageUrl?.removePrefix(imagePrefix)
fun String.addImagePrefix(): String? = imagePrefix + this