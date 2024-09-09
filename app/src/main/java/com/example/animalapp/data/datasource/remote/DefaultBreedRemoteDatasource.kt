package com.example.animalapp.data.datasource.remote

import com.example.animalapp.data.api.ApiService
import com.example.animalapp.data.common.LIMIT
import com.example.animalapp.data.utils.extensions.getApiError
import com.example.animalapp.data.model.remote.BreedDTO
import javax.inject.Inject

class DefaultBreedRemoteDatasource @Inject constructor(private val apiService: ApiService) :
    BreedRemoteDatasource {

    override suspend fun getBreeds(page: Int): Result<List<BreedDTO>> {
        val response = apiService.getBreeds(LIMIT, page)
        return if (response.isSuccessful)
            Result.success(response.body() ?: emptyList())
        else
            Result.failure(Exception(response.getApiError()?.message))
    }

    override suspend fun searchBreeds(breedName: String): Result<List<BreedDTO>> {
        val response = apiService.searchBreeds(breedName = breedName)
        return if (response.isSuccessful)
            Result.success(response.body() ?: emptyList())
        else
            Result.failure(Exception(response.getApiError()?.message))
    }
}