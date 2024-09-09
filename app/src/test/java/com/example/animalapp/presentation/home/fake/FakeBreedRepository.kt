package com.example.animalapp.presentation.home.fake

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.domain.mapper.toBreed
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBreedRepository : BreedRepository {

    private val breedEntities = mutableListOf<BreedEntity>()
    val breedsFlowList =
        listOf(BreedDTO.DEFAULT.toBreed(), BreedDTO.SECONDARY.toBreed().copy(numberOfOrders = 2))
    val searchBreedsFlowList =
        listOf(BreedDTO.DEFAULT.toBreed(), BreedDTO.SECONDARY.toBreed().copy(numberOfOrders = 2))

    fun getBreedEntities() = breedEntities.toList()

    override suspend fun fetchBreeds(page: Int) {

    }

    override fun breedsFlow(): Flow<List<Breed>> {
        return flowOf(breedsFlowList)
    }

    override fun searchBreeds(breedName: String): Flow<List<Breed>> {
        return flowOf(searchBreedsFlowList)
    }

    override suspend fun insertBreedEntity(breedEntity: BreedEntity) {
        breedEntities.add(breedEntity)
    }

    override suspend fun insertAllBreedEntities(breedEntities: List<BreedEntity>) {
        this.breedEntities.addAll(breedEntities)
    }

    override suspend fun update(breedId: String, isFavourite: Int) {
        breedEntities
            .map {
                if (it.id == breedId)
                    it.copy(isFavorite = if (isFavourite == 0) true else false)
                else
                    it
            }
    }

    override suspend fun removeAll() {
        breedEntities.clear()
    }

    override suspend fun remove(id: String) {
        breedEntities.removeIf { it.id == id }
    }
}