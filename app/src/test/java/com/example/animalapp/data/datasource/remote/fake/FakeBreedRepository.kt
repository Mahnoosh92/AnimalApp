package com.example.animalapp.data.datasource.remote.fake

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.mapper.toBreed
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBreedRepository : BreedRepository {

    private var breedEntities = mutableListOf<BreedEntity>()

    override suspend fun fetchBreeds(page: Int) {
        /*NO_OP*/
    }

    fun getBreedEntities() = breedEntities.toList()

    override fun breedsFlow(): Flow<List<Breed>> =
        flowOf(listOf(Breed.DEFAULT, Breed.SECONDARY))

    override fun searchBreeds(breedName: String): Flow<List<Breed>> {
        return flowOf(listOf(BreedDTO.DEFAULT.toBreed()))
    }

    override suspend fun insertBreedEntity(breedEntity: BreedEntity) {
        breedEntities.add(breedEntity)
    }

    override suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>) {
        this.breedEntities.addAll(breedEntities)
    }

    override suspend fun update(breedId: String, isFavourite: Boolean) {
        breedEntities = breedEntities
            .map {
                if (it.id == breedId)
                    it.copy(isFavorite = isFavourite)
                else
                    it
            }.toMutableList()
    }

    override suspend fun removeAll() {
        breedEntities.clear()
    }

    override suspend fun remove(id: String) {
        breedEntities.removeIf { it.id == id }
    }

    override suspend fun filterBreedsWithoutSync(page: Int): List<Breed> =
        listOf(Breed.DEFAULT)

    override fun filterBreedsWithSync(page: Int): Flow<List<Breed>> =
        flowOf(listOf(Breed.DEFAULT))

    override suspend fun fetchFilterBreeds(page: Int) {
        /*NO_OP*/
    }
}