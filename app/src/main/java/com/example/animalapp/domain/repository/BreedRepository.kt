package com.example.animalapp.domain.repository

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.domain.model.Breed
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
    suspend fun fetchBreeds(page: Int)
    fun breedsFlow(): Flow<List<Breed>>
    fun searchBreeds(breedName: String): Flow<List<Breed>>
    suspend fun insertBreedEntity(breedEntity: BreedEntity)
    suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>)
    suspend fun update(breedId: String, isFavourite: Int)
    suspend fun removeAll()
    suspend fun remove(id: String)
}