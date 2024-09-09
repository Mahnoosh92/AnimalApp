package com.example.animalapp.data.repository

import com.example.animalapp.data.datasource.local.BreedLocalDatasource
import com.example.animalapp.data.datasource.remote.BreedRemoteDatasource
import com.example.animalapp.data.di.IoDispatcher
import com.example.animalapp.data.extensions.syncWithLocalBreeds
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.domain.mapper.toBreed
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.repository.BreedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBreedRepository @Inject constructor(
    private val remoteDatasource: BreedRemoteDatasource,
    private val localDatasource: BreedLocalDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BreedRepository {


    private val remoteBreedsFlow = MutableStateFlow<List<Breed>?>(emptyList())

    override suspend fun fetchBreeds(page: Int) {
        val result = remoteDatasource.getBreeds(page = page)
        if (result.isSuccess) {
            val breeds = result.getOrNull()?.map { it.toBreed() } ?: emptyList()
            remoteBreedsFlow.value = (remoteBreedsFlow.value ?: emptyList<Breed>()) + breeds
        } else {
            throw Exception(result.exceptionOrNull()?.message)
        }
    }

    override fun breedsFlow(): Flow<List<Breed>> = combine(
        remoteBreedsFlow,
        localDatasource.getBreeds()
    ) { remoteBreeds, localBreeds ->
        remoteBreeds?.syncWithLocalBreeds(localBreeds.map { it.toBreed() }) ?: emptyList()
    }
        .catch {
            throw Exception(it.message)
        }
        .flowOn(ioDispatcher)

    override fun searchBreeds(breedName: String): Flow<List<Breed>> = localDatasource.getBreeds()
        .map { breedEntityList ->
            val response = remoteDatasource.searchBreeds(breedName = breedName)
            if (response.isSuccess) {
                response.getOrNull()?.map { it.toBreed() }
                    ?.syncWithLocalBreeds(breedEntityList.map { it.toBreed() }) ?: emptyList()
            } else {
                throw Exception(response.exceptionOrNull()?.message)
            }
        }


    override suspend fun insertBreedEntity(breedEntity: BreedEntity) {
        localDatasource.insertBreedEntity(breedEntity = breedEntity)
    }

    override suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>) {
        localDatasource.insertAllBreedEntities(breedEntities = breedEntities)
    }

    override suspend fun update(breedId: String, isFavourite: Int) =
        localDatasource.update(breedId = breedId, isFavourite = isFavourite)

    override suspend fun removeAll() = localDatasource.removeAll()
    override suspend fun remove(id: String) = localDatasource.remove(id = id)

}