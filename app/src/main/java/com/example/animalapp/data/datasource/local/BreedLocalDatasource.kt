package com.example.animalapp.data.datasource.local

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.BreedWithImage
import com.example.animalapp.data.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow

interface BreedLocalDatasource {
    suspend fun getBreeds(): Flow<List<BreedWithImage>>
    suspend fun insert(breedEntity: BreedEntity, imageEntity: ImageEntity)
    suspend fun remove(breedId: String)

}