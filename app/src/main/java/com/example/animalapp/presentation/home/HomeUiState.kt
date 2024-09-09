package com.example.animalapp.presentation.home

import com.example.animalapp.domain.model.Breed

data class HomeUiState(
    val breeds: List<Breed> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val numberOfItemsInCart: Int = 0
)
