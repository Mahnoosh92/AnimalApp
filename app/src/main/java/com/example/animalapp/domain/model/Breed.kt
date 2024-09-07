package com.example.animalapp.domain.model

data class Breed(
    val description: String?,
    val id: String,
    val name: String?,
    val numberOfOrders: Int = 0,
    val isFavorite: Boolean = false,
    val isAddedToCart: Boolean = false
)

data class Image(
    val height: Int?,
    val id: String,
    val url: String?,
    val width: Int?
)

data class BreedWithImage(
    val breed: Breed,
    val image: Image
)
