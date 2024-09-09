package com.example.animalapp.domain.model

data class Breed(
    val description: String?,
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val numberOfOrders: Int = 0,
    val isFavorite: Boolean = false,
    val isAddedToCart: Boolean = false
)

