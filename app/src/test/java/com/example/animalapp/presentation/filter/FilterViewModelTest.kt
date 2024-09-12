package com.example.animalapp.presentation.filter

import app.cash.turbine.test
import com.example.animalapp.data.datasource.remote.fake.FakeBreedRepository
import com.example.animalapp.domain.model.Breed
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FilterViewModelTest {

    private val breedRepository = FakeBreedRepository()

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var viewModel: FilterViewModel

    @Before
    fun setup() {
        viewModel = FilterViewModel(breedRepository, dispatcher)
    }

    @Test
    fun `should update ui state on init block `() = scope.runTest {

        dispatcher.scheduler.advanceUntilIdle()
        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(FilterUiState.BreedsList(breeds = listOf(Breed.DEFAULT)))
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `should update number of orders`() = scope.runTest {
        val selectedBreed = Breed.DEFAULT.copy(numberOfOrders = 3)
        viewModel.addNumberOfOrders(selectedBreed = selectedBreed)

        dispatcher.scheduler.advanceUntilIdle()
        viewModel.uiState.test {

            assertThat(awaitItem()).isEqualTo(
                FilterUiState.BreedsList(
                    breeds = listOf(
                        Breed.DEFAULT.copy(
                            numberOfOrders = selectedBreed.numberOfOrders + 1
                        )
                    )
                )
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}