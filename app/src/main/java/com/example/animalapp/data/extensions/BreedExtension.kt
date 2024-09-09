package com.example.animalapp.data.extensions

import com.example.animalapp.domain.model.Breed

fun List<Breed>.syncWithLocalBreeds(localBreeds: List<Breed>) = this.map { breed ->
    val matchLocalBreed = localBreeds.find { it.id == breed.id }
    if (matchLocalBreed != null) {
        breed.copy(
            isFavorite = matchLocalBreed.isFavorite,
            numberOfOrders = matchLocalBreed.numberOfOrders,
            isAddedToCart = matchLocalBreed.isAddedToCart
        )
    } else {
        breed
    }
}