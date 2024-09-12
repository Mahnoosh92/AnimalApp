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
    suspend fun update(breedId: String, isFavourite: Boolean)
    suspend fun removeAll()
    suspend fun remove(id: String)
    suspend fun filterBreedsWithoutSync(page: Int): List<Breed>
    fun filterBreedsWithSync(page: Int): Flow<List<Breed>>
    suspend fun fetchFilterBreeds(page: Int)
}