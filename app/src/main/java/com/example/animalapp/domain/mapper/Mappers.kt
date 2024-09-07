package com.example.animalapp.domain.mapper

import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.ImageEntity
import com.example.animalapp.data.model.remote.BreedDTO
import com.example.animalapp.data.model.remote.ImageDTO
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.model.BreedWithImage
import com.example.animalapp.domain.model.Image


fun ImageDTO.toImage() = Image(
    height = this.height,
    id = this.id,
    url = this.url,
    width = this.width
)


fun BreedDTO.toBreed() = Breed(
    description = this.description,
    id = this.id,
    name = this.name
)

fun BreedDTO.toBreedWithImage(isFavorite: Boolean = false) = BreedWithImage(
    breed = this.toBreed().copy(isFavorite = isFavorite),
    image = this.image.toImage()
)

fun BreedWithImage.toBreedEntityAndImageEntity(): Pair<ImageEntity, BreedEntity> {
    val imageEntity = ImageEntity(
        id = this.image.id,
        height = this.image.height,
        breedId = this.breed.id,
        url = this.image.url,
        width = this.image.width
    )
    val breedEntity = BreedEntity(
        description = this.breed.description,
        id = this.breed.id,
        name = this.breed.name
    )
    return (imageEntity to breedEntity)
}