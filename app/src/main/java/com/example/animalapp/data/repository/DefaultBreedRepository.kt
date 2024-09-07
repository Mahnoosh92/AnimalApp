package com.example.animalapp.data.repository

import com.example.animalapp.data.datasource.local.BreedLocalDatasource
import com.example.animalapp.data.datasource.remote.BreedRemoteDatasource
import com.example.animalapp.data.di.IoDispatcher
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.ImageEntity
import com.example.animalapp.domain.mapper.toBreedWithImage
import com.example.animalapp.domain.model.BreedWithImage
import com.example.animalapp.domain.repository.BreedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBreedRepository @Inject constructor(
    private val remoteDatasource: BreedRemoteDatasource,
    private val localDatasource: BreedLocalDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) : BreedRepository {

    private val mapOfLatestBreeds = mutableMapOf<Int, List<BreedWithImage>?>()

    override fun getBreeds(page: Int): Flow<List<BreedWithImage>?> {
        return localDatasource
            .getBreeds()
            .map { favouriteBreeds ->
                if (mapOfLatestBreeds[page] == null) {
                    val response = remoteDatasource.getBreeds(page = page)
                    if (response.isSuccess) {
                        mapOfLatestBreeds[page] =
                            response.getOrNull()?.map { it.toBreedWithImage() }
                    }
                }
                mapOfLatestBreeds[page]
                    ?.map { breedWithImage ->
                        if (
                            favouriteBreeds.find {
                                it.breed.id == breedWithImage.breed.id
                            } != null) {
                            breedWithImage.copy(breed = breedWithImage.breed.copy(isFavorite = true))
                        } else {
                            breedWithImage.copy(breed = breedWithImage.breed.copy(isFavorite = false))
                        }
                    }
            }
            .catch {
                emptyList<BreedWithImage>()
            }
            .flowOn(ioDispatcher)
    }

    override fun searchBreeds(breedName: String): Flow<List<BreedWithImage>?> = flow {
        val response = remoteDatasource.searchBreeds(breedName = breedName)
        if (response.isSuccess) {
            emit(response.getOrNull()?.map { it.toBreedWithImage(isFavorite = false) })
        } else {
            throw Exception(response.exceptionOrNull()?.message)
        }
    }

    override suspend fun insert(breedEntity: BreedEntity, imageEntity: ImageEntity) =
        localDatasource.insert(breedEntity = breedEntity, imageEntity = imageEntity)

    override suspend fun remove(breedId: String) = localDatasource.remove(breedId = breedId)
}