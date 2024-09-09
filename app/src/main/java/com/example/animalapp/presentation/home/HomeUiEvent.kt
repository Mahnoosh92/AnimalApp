package com.example.animalapp.presentation.home

import com.example.animalapp.domain.model.Breed


sealed interface HomeUiEvent {
    object LoadMoreBreeds : HomeUiEvent
    object SaveChangedBreedsWithImage : HomeUiEvent
    data class OnAddNumberOfOrdersClicked(val breed: Breed) : HomeUiEvent
    data class OnRemoveNumberOfOrdersClicked(val breed: Breed) : HomeUiEvent
    data class OnAddToCartClicked(val breed: Breed) : HomeUiEvent
    data class OnRemoveFromCartClicked(val breed: Breed) : HomeUiEvent
    data class OnFavouriteClicked(val breed: Breed) : HomeUiEvent
}