package com.example.animalapp.data.datasource.remote

import com.example.animalapp.data.api.ApiService
import com.example.animalapp.data.common.LIMIT
import com.example.animalapp.data.model.remote.BreedDTO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DefaultBreedRemoteDatasourceTest {

    @Mock
    lateinit var apiService: ApiService

    lateinit var breedRemoteDatasource: BreedRemoteDatasource

    private val sucessfulResponse = Response.success<List<BreedDTO>>(listOf(BreedDTO.DEFAULT))
    private val errorResponse = Response.error<List<BreedDTO>>(
        400,
        "{\"message\":\"somestuff\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    );

    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        breedRemoteDatasource = DefaultBreedRemoteDatasource(apiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should return list of breeds when response is successful`() = scope.runTest {
        val page = 1
        Mockito.`when`(apiService.getBreeds(LIMIT, page)).thenReturn(sucessfulResponse)

        val result = breedRemoteDatasource.getBreeds(page)

        assertThat(result.getOrNull()).isEqualTo(listOf(BreedDTO.DEFAULT))
    }

    @Test
    fun `should return failure when response is not successful`() = scope.runTest {
        val page = 1
        Mockito.`when`(apiService.getBreeds(LIMIT, page)).thenReturn(errorResponse)

        val result = breedRemoteDatasource.getBreeds(page)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("somestuff")
    }
}