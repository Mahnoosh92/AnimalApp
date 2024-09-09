package com.example.animalapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalapp.data.di.MainDispatcher
import com.example.animalapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _breedQueryState = MutableStateFlow("")
    val breedQueryState = _breedQueryState.asStateFlow()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val handler = CoroutineExceptionHandler { _, exception ->
        _uiState.update { oldUiState ->
            oldUiState.copy(error = exception.message, isLoading = false)
        }
    }

    init {
        searchBreeds()
    }

    private fun searchBreeds() {
        viewModelScope.launch(mainDispatcher + handler) {
            _breedQueryState
                .filter { it != "" }
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    breedRepository.searchBreeds(query)
                }
                .collect { breeds ->
                    _uiState.update { oldUiState ->
                        oldUiState.copy(breeds = breeds)
                    }
                }
        }
    }

    fun onSearchValueChanged(breedName: String) {
        _breedQueryState.value = breedName
    }
}