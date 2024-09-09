package com.example.animalapp.domain.mapper

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.model.Breed


fun BreedDTO.toBreed() = Breed(
    description = this.description,
    id = this.id,
    name = this.name,
    imageUrl = this.image?.url
)


fun BreedEntity.toBreed() = Breed(
    description = this.description,
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    isFavorite = this.isFavorite,
    isAddedToCart = this.isAddedToCart,
    numberOfOrders = this.numberOfOrders
)

fun Breed.toBreedEntity(isFavorite: Boolean = this.isFavorite) = BreedEntity(
    description = this.description,
    name = this.name,
    id = this.id,
    isFavorite = this.isFavorite,
    isAddedToCart = this.isAddedToCart,
    numberOfOrders = this.numberOfOrders,
    imageUrl = this.imageUrl
)