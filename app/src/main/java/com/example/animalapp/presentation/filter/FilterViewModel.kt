package com.example.animalapp.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalapp.data.di.MainDispatcher
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    val _uiState = MutableStateFlow<FilterUiState>(FilterUiState.Loading)
    val uiState = _uiState.asStateFlow()
    var page = 0

    init {
        viewModelScope.launch(mainDispatcher) {
            getBreeds(page = page)
            breedRepository.filterBreedsWithSync(page = page)
                .collect {
                    _uiState.value =
                        FilterUiState.BreedsList(breeds = _uiState.value.getBreeds() + it)
                }
        }
    }

    private fun getBreeds(page: Int) {
        viewModelScope.launch(mainDispatcher) {
            breedRepository.fetchFilterBreeds(page)
        }
    }

    fun getMoreBreeds() {
        page++
        viewModelScope.launch(mainDispatcher) {
            breedRepository.fetchFilterBreeds(page)
        }
    }

    fun addNumberOfOrders(selectedBreed: Breed) {
        _uiState.value = FilterUiState.BreedsList(breeds = _uiState.value.getBreeds().map {
            if (it.id == selectedBreed.id)
                selectedBreed.copy(numberOfOrders = selectedBreed.numberOfOrders + 1)
            else
                it
        })
    }

}

fun FilterUiState.getBreeds() = if (this is FilterUiState.BreedsList) this.breeds else emptyList()

