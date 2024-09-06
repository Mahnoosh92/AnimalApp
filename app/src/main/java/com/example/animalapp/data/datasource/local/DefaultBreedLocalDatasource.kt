package com.example.animalapp.data.datasource.local

import com.example.animalapp.data.db.dao.BreedDao
import com.example.animalapp.data.db.dao.ImageDao
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.BreedWithImage
import com.example.animalapp.data.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultBreedLocalDatasource @Inject constructor(
    private val breedDao: BreedDao,
    private val imageDao: ImageDao
) :
    BreedLocalDatasource {
    override suspend fun getBreeds(): Flow<List<BreedWithImage>> = breedDao.getUsersWithPlaylists()

    override suspend fun insert(breedEntity: BreedEntity, imageEntity: ImageEntity) {
        breedDao.insert(breedEntity = breedEntity)
        imageDao.insert(imageEntity = imageEntity)
    }

    override suspend fun remove(breedId: String) = breedDao.remove(id = breedId)
}