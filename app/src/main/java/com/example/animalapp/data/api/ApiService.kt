package com.example.animalapp.data.api

import com.example.animalapp.data.model.remote.BreedDTO
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<List<BreedDTO>>

    @GET("breeds/search")
    suspend fun searchBreeds(@Query("q") breedName: String): Response<List<BreedDTO>>
}