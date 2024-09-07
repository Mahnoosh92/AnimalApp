package com.example.animalapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalapp.data.di.MainDispatcher
import com.example.animalapp.domain.mapper.toBreedEntityAndImageEntity
import com.example.animalapp.domain.model.BreedWithImage
import com.example.animalapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    init {
        loadBreeds()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.LoadMoreBreeds -> {
                loadMoreBreeds()
            }

            is HomeUiEvent.OnAddNumberOfOrdersClicked -> {
                addNumberOfOrders(event.breedWithImage)
            }

            is HomeUiEvent.OnRemoveNumberOfOrdersClicked -> {
                removeNumberOfOrders(event.breedWithImage)
            }

            is HomeUiEvent.OnAddToCartClicked -> {
                addToCart(event.breedWithImage)
            }

            is HomeUiEvent.OnRemoveFromCartClicked -> {
                removeFromCart(event.breedWithImage)
            }

            is HomeUiEvent.OnFavouriteClicked -> {
                toggleFavouriteStatus(event.breedWithImage)
            }
        }
    }

    private fun loadBreeds() {
        viewModelScope.launch(mainDispatcher) {
            breedRepository.getBreeds(page = page)
                .collect { breeds ->
                    breeds?.let { notNullRemoteBreeds ->
                        _uiState.update { oldUiState ->
                            oldUiState.copy(
                                breeds =
                                oldUiState.breeds?.let { notNullOldBreeds ->
                                    notNullRemoteBreeds
                                        .map { breed ->
                                            val oldBreed =
                                                notNullOldBreeds.find { it.breed.id == breed.breed.id }
                                            oldBreed?.let {
                                                oldBreed.copy(breed = oldBreed.breed.copy(isFavorite = breed.breed.isFavorite))
                                            } ?: run {
                                                breed
                                            }
                                        }
                                } ?: run { notNullRemoteBreeds },
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    private fun loadMoreBreeds() {
        page += 1
        viewModelScope.launch(mainDispatcher) {
            breedRepository.getBreeds(page = page)
                .catch {

                }
                .collect { breeds ->
                    _uiState.update { oldUiState ->
                        val mutableList = oldUiState.breeds?.toMutableList()
                        mutableList?.addAll(breeds?.toMutableList() ?: emptyList())
                        oldUiState.copy(breeds = mutableList, isLoading = false)
                    }
                }
        }
    }

    private fun addNumberOfOrders(breedWithImage: BreedWithImage) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                breeds = oldUiState
                    .breeds
                    ?.map { breed ->
                        if (breed.breed.id == breedWithImage.breed.id)
                            breed.copy(breed = breedWithImage.breed.copy(numberOfOrders = breedWithImage.breed.numberOfOrders + 1))
                        else
                            breed
                    }
            )
        }
    }

    private fun removeNumberOfOrders(breedWithImage: BreedWithImage) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                breeds = oldUiState
                    .breeds
                    ?.map { breed ->
                        if (breed.breed.id == breedWithImage.breed.id)
                            if (breedWithImage.breed.numberOfOrders > 0)
                                breed.copy(breed = breedWithImage.breed.copy(numberOfOrders = breedWithImage.breed.numberOfOrders - 1))
                            else
                                breed
                        else
                            breed
                    }
            )
        }
    }

    private fun addToCart(breedWithImage: BreedWithImage) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                breeds = oldUiState
                    .breeds
                    ?.map { breed ->
                        if (breed.breed.id == breedWithImage.breed.id)
                            if (!breed.breed.isAddedToCart)
                                breed.copy(breed = breedWithImage.breed.copy(isAddedToCart = true))
                            else
                                breed
                        else
                            breed
                    },
                numberOfItemsInCart = oldUiState.numberOfItemsInCart + 1
            )
        }
    }

    private fun removeFromCart(breedWithImage: BreedWithImage) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                breeds = oldUiState
                    .breeds
                    ?.map { breed ->
                        if (breed.breed.id == breedWithImage.breed.id)
                            if (breedWithImage.breed.isAddedToCart)
                                breed.copy(breed = breedWithImage.breed.copy(isAddedToCart = false))
                            else
                                breed
                        else
                            breed
                    },
                numberOfItemsInCart = if (oldUiState.numberOfItemsInCart > 0) oldUiState.numberOfItemsInCart - 1 else oldUiState.numberOfItemsInCart
            )
        }
    }

    private fun toggleFavouriteStatus(breedWithImage: BreedWithImage) {
        viewModelScope.launch(mainDispatcher) {
            if (breedWithImage.breed.isFavorite)
                breedRepository.remove(breedId = breedWithImage.breed.id)
            else {
                val (imageEntity, breedEntity) = breedWithImage.toBreedEntityAndImageEntity()
                breedRepository.insert(imageEntity = imageEntity, breedEntity = breedEntity)
            }
        }
    }
}