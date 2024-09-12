package com.example.animalapp.presentation.search

import app.cash.turbine.test
import com.example.animalapp.data.datasource.remote.fake.FakeBreedRepository
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.mapper.toBreed
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SearchViewModelTest {

    private val breedRepository = FakeBreedRepository()

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(breedRepository, dispatcher)
    }

    @Test
    fun `should call searchBreeds when search query has changed`() = scope.runTest {
        val query = "sample"

        searchViewModel.onSearchValueChanged(query)
        dispatcher.scheduler.advanceUntilIdle()
        searchViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                SearchUiState(
                    breeds = listOf(BreedDTO.DEFAULT.toBreed()),
                    error = null,
                    isLoading = false
                )
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}