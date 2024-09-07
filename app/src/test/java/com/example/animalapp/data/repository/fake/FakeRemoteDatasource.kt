package com.example.animalapp.data.repository.fake

import com.example.animalapp.data.datasource.remote.BreedRemoteDatasource
import com.example.animalapp.data.model.remote.BreedDTO

class FakeRemoteDatasource : BreedRemoteDatasource {
    override suspend fun getBreeds(page: Int): Result<List<BreedDTO>> =
        Result.success(listOf(BreedDTO.SECONDARY, BreedDTO.DEFAULT))

    override suspend fun searchBreeds(breedName: String): Result<List<BreedDTO>> =
        Result.success(listOf(BreedDTO.DEFAULT, BreedDTO.SECONDARY))
}