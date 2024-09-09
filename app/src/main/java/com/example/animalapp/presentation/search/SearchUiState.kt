package com.example.animalapp.presentation.search

import com.example.animalapp.domain.model.Breed

data class SearchUiState(
    val breeds: List<Breed> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)