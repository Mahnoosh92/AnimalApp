package com.example.animalapp.data.datasource.local

import com.example.animalapp.data.db.dao.BreedDao
import com.example.animalapp.data.model.local.BreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultBreedLocalDatasource @Inject constructor(
    private val breedDao: BreedDao
) :
    BreedLocalDatasource {
    override fun getBreeds(): Flow<List<BreedEntity>> = breedDao.getBreedsWithImages()
    override suspend fun insertBreedEntity(breedEntity: BreedEntity) {
        breedDao.insert(breedEntity = breedEntity)
    }

    override suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>) {
        breedDao.insertAll(breedEntities = breedEntities)
    }

    override suspend fun update(breedId: String, isFavourite: Boolean) =
        breedDao.update(id = breedId, isFavorite = isFavourite)

    override suspend fun removeAll() = breedDao.removeAll()
    override suspend fun remove(id: String) = breedDao.remove(id = id)
}