package com.example.animalapp.presentation.home

import com.example.animalapp.domain.model.BreedWithImage

data class HomeUiState(
    val breeds: List<BreedWithImage>? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val numberOfItemsInCart: Int = 0
)
