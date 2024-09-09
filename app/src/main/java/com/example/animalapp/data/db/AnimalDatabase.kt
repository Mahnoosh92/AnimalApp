package com.example.animalapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animalapp.data.db.dao.BreedDao
import com.example.animalapp.data.model.local.BreedEntity


@Database(entities = [BreedEntity::class], version = 3)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun BreedDao(): BreedDao
}