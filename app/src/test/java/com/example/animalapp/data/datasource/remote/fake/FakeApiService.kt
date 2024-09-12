package com.example.animalapp.data.datasource.remote.fake

import com.example.animalapp.data.api.ApiService
import com.example.animalapp.data.model.remote.BreedDTO
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeApiService : ApiService {
    private var getBreedApiCallIsSuccessful = true
    fun setGetBreedApiCallStatus(isSuccessful: Boolean) {
        getBreedApiCallIsSuccessful = isSuccessful
    }

    override suspend fun getBreeds(limit: Int, page: Int): Response<List<BreedDTO>> {
        return if (getBreedApiCallIsSuccessful) {
            Response.success(listOf(BreedDTO.DEFAULT, BreedDTO.SECONDARY))
        } else {
            Response.error<List<BreedDTO>>(
                404, "{\"message\":\"${errorMessage}\"}"
                    .toResponseBody()
            )
        }
    }

    override suspend fun searchBreeds(breedName: String): Response<List<BreedDTO>> {
        return if (getBreedApiCallIsSuccessful) {
            Response.success(listOf(BreedDTO.DEFAULT, BreedDTO.SECONDARY))
        } else {
            Response.error<List<BreedDTO>>(
                404, "{\"message\":\"somestuff\"}"
                    .toResponseBody()
            )
        }
    }
}