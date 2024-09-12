package com.example.animalapp.presentation.filter

import com.example.animalapp.domain.model.Breed

sealed interface FilterUiState {
    object Loading : FilterUiState
    data class Failure(val error: String) : FilterUiState
    data class BreedsList(
        val breeds: List<Breed>
    ) : FilterUiState
}