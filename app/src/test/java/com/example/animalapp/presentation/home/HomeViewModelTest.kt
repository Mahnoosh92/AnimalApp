package com.example.animalapp.presentation.home

import app.cash.turbine.test
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.mapper.toBreed
import com.example.animalapp.domain.mapper.toBreedEntity
import com.example.animalapp.presentation.home.fake.FakeBreedRepository
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
            breedRepository.insertBreedEntity(BreedDTO.DEFAULT.toBreed().toBreedEntity())

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
                    BreedDTO.DEFAULT.toBreed()
                )
            )

            advanceUntilIdle()
            viewModel.uiState.test {
                val item = awaitItem().breeds.find { it.id == BreedDTO.DEFAULT.id }
                assertThat(item?.numberOfOrders).isEqualTo(1)
            }
        }

    @Test
    fun `should decline number of orders by 1 when add number button is called`() =
        scope.runTest {

            viewModel.onEvent(
                HomeUiEvent.OnRemoveNumberOfOrdersClicked(
                    BreedDTO.SECONDARY.toBreed()
                )
            )

            advanceUntilIdle()
            viewModel.uiState.test {
                val item = awaitItem().breeds.find { it.id == BreedDTO.SECONDARY.id }
                assertThat(item?.numberOfOrders).isEqualTo(1)
            }
        }
}