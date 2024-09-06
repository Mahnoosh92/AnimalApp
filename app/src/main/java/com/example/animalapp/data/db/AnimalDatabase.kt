package com.example.animalapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animalapp.data.db.dao.BreedDao
import com.example.animalapp.data.db.dao.ImageDao
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.ImageEntity


@Database(entities = [ImageEntity::class, BreedEntity::class], version = 1)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun ImageDao(): ImageDao
    abstract fun BreedDao(): BreedDao
}