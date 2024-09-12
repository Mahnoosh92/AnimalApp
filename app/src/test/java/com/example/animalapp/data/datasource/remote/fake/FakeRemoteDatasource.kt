package com.example.animalapp.data.datasource.remote.fake

import com.example.animalapp.data.datasource.remote.BreedRemoteDatasource
import com.example.animalapp.data.model.remote.BreedDTO

class FakeRemoteDatasource : BreedRemoteDatasource {

    private var getBreedApiCallIsSuccessful = true
    fun setGetBreedApiCallStatus(isSuccessful: Boolean) {
        getBreedApiCallIsSuccessful = isSuccessful
    }

    override suspend fun getBreeds(page: Int): Result<List<BreedDTO>> {
        return if (getBreedApiCallIsSuccessful) {
            if (page == 0)
                Result.success(listOf(BreedDTO.DEFAULT))
            else
                Result.success(listOf(BreedDTO.SECONDARY))
        } else {
            Result.failure(Exception(errorMessage))
        }
    }

    override suspend fun searchBreeds(breedName: String): Result<List<BreedDTO>> {
        return if (getBreedApiCallIsSuccessful) {
            Result.success(listOf(BreedDTO.DEFAULT, BreedDTO.SECONDARY))
        } else {
            Result.failure(Exception(errorMessage))
        }
    }
}