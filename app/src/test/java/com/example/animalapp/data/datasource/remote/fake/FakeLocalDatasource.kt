package com.example.animalapp.data.datasource.remote.fake

import com.example.animalapp.data.datasource.local.BreedLocalDatasource
import com.example.animalapp.data.model.local.BreedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDatasource : BreedLocalDatasource {

    private var isBreedLocalDatasourceEmpty = true

    fun setIsBreedLocalDatasourceEmpty(isEmpty: Boolean) {
        isBreedLocalDatasourceEmpty = isEmpty
    }

    private var breedEntities = mutableListOf<BreedEntity>()

    fun getBreedEntities() = breedEntities.toList()

    override fun getBreeds(): Flow<List<BreedEntity>> {
        if (isBreedLocalDatasourceEmpty) return flowOf(emptyList())
        return flowOf(listOf(BreedEntity.DEFAULT.copy(isFavorite = true)))
    }

    override suspend fun insertBreedEntity(breedEntity: BreedEntity) {
        breedEntities.add(breedEntity)
    }

    override suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>) {
        this.breedEntities.addAll(breedEntities)
    }

    override suspend fun update(breedId: String, isFavourite: Boolean) {
        breedEntities = breedEntities.map {
            if (it.id == breedId) {
                it.copy(isFavorite = isFavourite)
            } else {
                it
            }
        }.toMutableList()
    }

    override suspend fun removeAll() {
        breedEntities.clear()
    }

    override suspend fun remove(id: String) {
        breedEntities.removeIf { it.id == id }
    }
}