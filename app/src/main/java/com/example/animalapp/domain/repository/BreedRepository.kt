package com.example.animalapp.domain.repository

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.ImageEntity
import com.example.animalapp.domain.model.BreedWithImage
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
    fun getBreeds(page: Int): Flow<List<BreedWithImage>?>
    fun searchBreeds(breedName: String): Flow<List<BreedWithImage>?>
    suspend fun insert(breedEntity: BreedEntity, imageEntity: ImageEntity)
    suspend fun remove(breedId: String)
}