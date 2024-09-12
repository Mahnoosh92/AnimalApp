package com.example.animalapp.domain.model

import com.example.animalapp.data.common.DEFAULT_BREED_ID
import com.example.animalapp.data.common.SECONDARY_BREED_ID

data class Breed(
    val description: String?,
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val numberOfOrders: Int = 0,
    val isFavorite: Boolean = false,
    val isAddedToCart: Boolean = false
){
    companion object{
        val DEFAULT = Breed(description = "", id= DEFAULT_BREED_ID, name = "", imageUrl = "", numberOfOrders = 2, isFavorite = false, isAddedToCart = false)
        val SECONDARY = Breed(description = "", id= SECONDARY_BREED_ID, name = "", imageUrl = "", numberOfOrders = 4, isFavorite = true, isAddedToCart = true)
    }
}

