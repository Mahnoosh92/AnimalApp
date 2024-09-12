package com.example.animalapp.presentation.home

import app.cash.turbine.test
import com.example.animalapp.data.datasource.remote.fake.FakeBreedRepository
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.mapper.toBreed
import com.example.animalapp.domain.mapper.toBreedEntity
import com.example.animalapp.domain.model.Breed
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private val breedRepository = FakeBreedRepository()

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var viewModel: HomeViewModel

    private val page = 0

    @Before
    fun setup() {
        viewModel = HomeViewModel(breedRepository, dispatcher)
    }

    @Test
    fun `should call update function when isFavourite is true and favourite button is clicked`() =
        scope.runTest {
            breedRepository.insertBreedEntity(
                BreedDTO.DEFAULT.toBreed().toBreedEntity().copy(isFavorite = true)
            )

            viewModel.onEvent(
                HomeUiEvent.OnFavouriteClicked(
                    BreedDTO.DEFAULT.toBreed().copy(isFavorite = true)
                )
            )

            advanceUntilIdle()
            val updatedItem =
                breedRepository.getBreedEntities().find { it.id == BreedDTO.DEFAULT.id }
            assertThat(updatedItem?.isFavorite).isFalse()
        }

    @Test
    fun `should call insertBreedEntity function when isFavourite is false and favourite button is clicked`() =
        scope.runTest {

            viewModel.onEvent(
                HomeUiEvent.OnFavouriteClicked(
                    BreedDTO.DEFAULT.toBreed().copy(isFavorite = false)
                )
            )

            advanceUntilIdle()
            val updatedItem =
                breedRepository.getBreedEntities().find { it.id == BreedDTO.DEFAULT.id }
            assertThat(updatedItem?.isFavorite).isTrue()
        }

    @Test
    fun `should increase number of orders by 1 when add number button is called`() =
        scope.runTest {

            viewModel.onEvent(
                HomeUiEvent.OnAddNumberOfOrdersClicked(
                    Breed.DEFAULT
                )
            )

            viewModel.uiState.test {
                val selectedItemNumberOfOrders =
                    awaitItem().breeds.find { it.id == Breed.DEFAULT.id }?.numberOfOrders ?: -1
                assertThat(selectedItemNumberOfOrders).isEqualTo(Breed.DEFAULT.numberOfOrders + 1)
                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `should decline number of orders by 1 when add number button is called`() =
        scope.runTest {

            viewModel.onEvent(
                HomeUiEvent.OnRemoveNumberOfOrdersClicked(
                    Breed.DEFAULT
                )
            )
            dispatcher.scheduler.advanceUntilIdle()
            viewModel.uiState.test {
                val selectedItemNumberOfOrders =
                    awaitItem().breeds.find { it.id == Breed.DEFAULT.id }?.numberOfOrders ?: -1
                assertThat(selectedItemNumberOfOrders).isEqualTo(Breed.DEFAULT.numberOfOrders - 1)
                cancelAndIgnoreRemainingEvents()
            }
        }
}