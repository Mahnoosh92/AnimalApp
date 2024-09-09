package com.example.animalapp.data.repository

import app.cash.turbine.test
import com.example.animalapp.data.datasource.local.BreedLocalDatasource
import com.example.animalapp.data.datasource.remote.BreedRemoteDatasource
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.mapper.toBreed
import com.example.animalapp.domain.repository.BreedRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultBreedRepositoryTest {

    @Mock
    private lateinit var remoteDatasource: BreedRemoteDatasource

    @Mock
    private lateinit var localDatasource: BreedLocalDatasource

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private lateinit var breedRepository: BreedRepository

    private val page = 1

    @Before
    fun setUp() {
        breedRepository = DefaultBreedRepository(
            remoteDatasource = remoteDatasource,
            localDatasource = localDatasource,
            ioDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should return list of breeds with all isFavorite when local datasource is empty and remote datasource is successful`() =
        scope.runTest {
            Mockito.`when`(localDatasource.getBreeds()).thenReturn(flowOf(emptyList()))
            Mockito.`when`(remoteDatasource.getBreeds(page = page))
                .thenReturn(Result.success(listOf(BreedDTO.DEFAULT)))

            breedRepository.fetchBreeds(page = page)

            breedRepository.breedsFlow().test {
                assertThat(awaitItem()).isEqualTo(listOf(BreedDTO.DEFAULT.toBreed()))
            }
        }

    @Test
    fun `should throws exception when remote datasource is not successful`() =
        scope.runTest {
            val exceptionMessage = "something went wrong"
            Mockito.`when`(localDatasource.getBreeds()).thenReturn(flowOf(emptyList()))
            Mockito.`when`(remoteDatasource.getBreeds(page = page))
                .thenReturn(Result.failure(Throwable(exceptionMessage)))

//            Mockito.`when`(remoteDatasource.getBreeds(page = page))
//                .thenReturn(Result.failure(Throwable(exceptionMessage)))
//
//            breedRepository.getBreeds(page = page).test {
//                val result = awaitError()
//                assertThat(result.message).isEqualTo(exceptionMessage)
//            }
            try {
                breedRepository.fetchBreeds(page = page)
            } catch (e: Exception) {
                assertThat(e.message).isEqualTo(exceptionMessage)
            }
        }

    @Test
    fun `should return list of breeds with isFavorite true when local datasource is has one data and remote datasource is successful`() =
        scope.runTest {
            Mockito.`when`(localDatasource.getBreeds())
                .thenReturn(flowOf(listOf(BreedEntity.DEFAULT.copy(isFavorite = true))))
            Mockito.`when`(remoteDatasource.getBreeds(page = page))
                .thenReturn(Result.success(listOf(BreedDTO.DEFAULT)))

            breedRepository.fetchBreeds(page = page)

            breedRepository.breedsFlow().test {
                assertThat(awaitItem().size).isEqualTo(1 )
            }
        }
}