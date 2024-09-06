package com.example.animalapp.data.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class BreedWithImage(
    @Embedded val breed: BreedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "breed_id"
    )
    val image: ImageEntity
)
