package com.example.animalapp.data.datasource.remote

import com.example.animalapp.data.model.remote.BreedDTO

interface BreedRemoteDatasource {

    suspend fun getBreeds(page: Int): Result<List<BreedDTO>>
    suspend fun searchBreeds(breedName: String): Result<List<BreedDTO>>
}