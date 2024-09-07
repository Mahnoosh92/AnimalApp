package com.example.animalapp.data.repository.fake

import com.example.animalapp.data.datasource.local.BreedLocalDatasource
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.ImageEntity
import com.example.animalapp.data.model.local.LocalBreedWithImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource : BreedLocalDatasource {

    private val breeds = mutableListOf<BreedEntity>()
    private val images = mutableListOf<ImageEntity>()

    override fun getBreeds(): Flow<List<LocalBreedWithImage>> =
        flowOf(listOf(LocalBreedWithImage.DEFAULT))

    override suspend fun insert(breedEntity: BreedEntity, imageEntity: ImageEntity) {
        breeds.add(breedEntity)
        images.add(imageEntity)
    }

    override suspend fun remove(breedId: String) {
        val breed = breeds.find { it.id == breedId }
        breed?.let {
            breeds.remove(breed)
        }
    }
}