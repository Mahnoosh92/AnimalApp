package com.example.animalapp.data.datasource.local

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.LocalBreedWithImage
import com.example.animalapp.data.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow

interface BreedLocalDatasource {
    fun getBreeds(): Flow<List<LocalBreedWithImage>>
    suspend fun insert(breedEntity: BreedEntity, imageEntity: ImageEntity)
    suspend fun remove(breedId: String)

}