package com.example.animalapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalapp.data.di.MainDispatcher
import com.example.animalapp.domain.mapper.toBreedEntity
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState = _uiState.asStateFlow()

    private var page = 0

    private val handler = CoroutineExceptionHandler { _, exception ->
        _uiState.update { oldUiState ->
            oldUiState.copy(error = exception.message, isLoading = false)
        }
    }

    init {
        loadBreeds()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.LoadMoreBreeds -> {
                loadMoreBreeds()
            }

            is HomeUiEvent.OnAddNumberOfOrdersClicked -> {
                addNumberOfOrders(event.breed)
            }

            is HomeUiEvent.OnRemoveNumberOfOrdersClicked -> {
                removeNumberOfOrders(event.breed)
            }

            is HomeUiEvent.OnAddToCartClicked -> {
                addToCart(event.breed)
            }

            is HomeUiEvent.OnRemoveFromCartClicked -> {
                removeFromCart(event.breed)
            }

            is HomeUiEvent.OnFavouriteClicked -> {
                toggleFavouriteStatus(event.breed)
            }

            is HomeUiEvent.SaveChangedBreedsWithImage -> {
                removeAllZeroFlagsBreeds()
                saveChangedBreeds()
            }
        }
    }

    private fun saveChangedBreeds() {
        viewModelScope.launch(mainDispatcher + handler) {
            val anyBreedsWithFilledFlags =
                _uiState.value.breeds
                    ?.filter { it.isAddedToCart || it.isFavorite || it.numberOfOrders > 0 }
                    ?.map { it.toBreedEntity() }
            anyBreedsWithFilledFlags?.let {
                breedRepository.insertAllBreedEntities(it)
            }
        }
    }

    private fun removeAllZeroFlagsBreeds() {
        viewModelScope.launch(mainDispatcher + handler) {
            val allZeroFlagsBreeds =
                _uiState.value.breeds.filter { !it.isAddedToCart && !it.isFavorite && it.numberOfOrders == 0 }
            allZeroFlagsBreeds.forEach {
                breedRepository.remove(it.id)
            }
        }
    }

    private fun loadBreeds() {
        viewModelScope.launch(mainDispatcher + handler) {
            breedRepository.fetchBreeds(page = 0)
            breedRepository.breedsFlow()
                .collect { breeds ->
                    _uiState.update { oldUiState ->
                        oldUiState.copy(
                            breeds = breeds,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun loadMoreBreeds() {
        page += 1
        viewModelScope.launch(mainDispatcher + handler) {
            breedRepository.fetchBreeds(page = page)
        }
    }

    private fun addNumberOfOrders(selectedBreed: Breed) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                breeds = oldUiState
                    .breeds
                    .map { breed ->
                        if (breed.id == selectedBreed.id)
                            breed.copy(numberOfOrders = breed.numberOfOrders + 1)
                        else
                            breed
                    }
            )
        }
    }

    private fun removeNumberOfOrders(selectedBreed: Breed) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                breeds = oldUiState
                    .breeds
                    .map { breed ->
                        if (breed.id == selectedBreed.id)
                            if (breed.numberOfOrders > 0)
                                breed.copy(numberOfOrders = breed.numberOfOrders - 1)
                            else
                                breed
                        else
                            breed
                    }
            )
        }
    }

    private fun addToCart(selectedBreed: Breed) {
        _uiState.update { oldUiState ->
            val newBreeds = oldUiState
                .breeds
                .map { breed ->
                    if (breed.id == selectedBreed.id)
                        if (!breed.isAddedToCart)
                            breed.copy(isAddedToCart = true)
                        else
                            breed
                    else
                        breed
                }
            oldUiState.copy(
                breeds = newBreeds,
                numberOfItemsInCart = newBreeds?.filter { it.isAddedToCart }?.size ?: 0
            )
        }
    }

    private fun removeFromCart(selectedBreed: Breed) {
        _uiState.update { oldUiState ->
            val newBreeds = oldUiState
                .breeds
                .map { breed ->
                    if (breed.id == selectedBreed.id)
                        if (breed.isAddedToCart)
                            breed.copy(isAddedToCart = false)
                        else
                            breed
                    else
                        breed
                }
            oldUiState.copy(
                breeds = newBreeds,
                numberOfItemsInCart = newBreeds?.filter { it.isAddedToCart }?.size ?: 0
            )
        }
    }

    private fun toggleFavouriteStatus(selectedBreed: Breed) {
        viewModelScope.launch(mainDispatcher + handler) {
            if (selectedBreed.isFavorite) {
                breedRepository.update(breedId = selectedBreed.id, isFavourite = 0)
            } else {
                breedRepository.insertBreedEntity(
                    breedEntity = selectedBreed.toBreedEntity().copy(
                        isFavorite = true
                    )
                )

            }
        }
    }
}