package com.example.animalapp.data.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class LocalBreedWithImage(
    @Embedded val breed: BreedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "breed_id"
    )
    val image: ImageEntity?
) {
    companion object {
        val DEFAULT = LocalBreedWithImage(BreedEntity.DEFAULT, ImageEntity.DEFAULT)
        val SECONDARY = LocalBreedWithImage(BreedEntity.SECONDARY, ImageEntity.SECONDARY)
    }
}
