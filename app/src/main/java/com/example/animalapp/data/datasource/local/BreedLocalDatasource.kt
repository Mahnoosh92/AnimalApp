package com.example.animalapp.data.datasource.local

import com.example.animalapp.data.model.local.BreedEntity
import kotlinx.coroutines.flow.Flow

interface BreedLocalDatasource {
    fun getBreeds(): Flow<List<BreedEntity>>
    suspend fun insertBreedEntity(breedEntity: BreedEntity)
    suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>)
    suspend fun update(breedId: String, isFavourite: Int)
    suspend fun removeAll()
    suspend fun remove(id: String)

}