package com.example.animalapp.presentation.home

import com.example.animalapp.domain.model.BreedWithImage

sealed interface HomeUiEvent {
    object LoadMoreBreeds : HomeUiEvent
    data class OnAddNumberOfOrdersClicked(val breedWithImage: BreedWithImage) : HomeUiEvent
    data class OnRemoveNumberOfOrdersClicked(val breedWithImage: BreedWithImage) : HomeUiEvent
    data class OnAddToCartClicked(val breedWithImage: BreedWithImage) : HomeUiEvent
    data class OnRemoveFromCartClicked(val breedWithImage: BreedWithImage) : HomeUiEvent
    data class OnFavouriteClicked(val breedWithImage: BreedWithImage) : HomeUiEvent
}