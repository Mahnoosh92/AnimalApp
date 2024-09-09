package com.example.animalapp.presentation.search

import app.cash.turbine.test
import com.example.animalapp.presentation.home.fake.FakeBreedRepository
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

        advanceUntilIdle()
        searchViewModel.uiState.test {
            val expectedBreeds = awaitItem().breeds
            assertThat(expectedBreeds).isEqualTo(breedRepository.searchBreedsFlowList)
        }
    }
}