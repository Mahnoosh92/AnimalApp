package com.example.animalapp.data.datasource.remote

import com.example.animalapp.data.datasource.remote.fake.FakeApiService
import com.example.animalapp.data.datasource.remote.fake.errorMessage
import com.example.animalapp.data.model.remote.BreedDTO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class DefaultBreedRemoteDatasourceTest {

    private val apiService = FakeApiService()

    lateinit var breedRemoteDatasource: BreedRemoteDatasource

    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)
    val page = 1

    @Before
    fun setUp() {
        breedRemoteDatasource = DefaultBreedRemoteDatasource(apiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should return list of breeds when response is successful`() = scope.runTest {
        apiService.setGetBreedApiCallStatus(isSuccessful = true)

        val result = breedRemoteDatasource.getBreeds(page)

        assertThat(result.getOrNull()).isEqualTo(listOf(BreedDTO.DEFAULT, BreedDTO.SECONDARY))
    }

    @Test
    fun `should return failure when response is not successful`() = scope.runTest {
        apiService.setGetBreedApiCallStatus(isSuccessful = false)

        val result = breedRemoteDatasource.getBreeds(page)


        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo(errorMessage)
    }
}