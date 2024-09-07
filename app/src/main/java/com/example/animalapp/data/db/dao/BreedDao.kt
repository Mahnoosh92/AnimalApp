package com.example.animalapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.animalapp.data.model.local.BreedEntity
import com.example.animalapp.data.model.local.LocalBreedWithImage
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Transaction
    @Query("SELECT * FROM breed_table")
    fun getBreedsWithImages(): Flow<List<LocalBreedWithImage>>

    @Query("SELECT * FROM breed_table")
    fun getBreeds(): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breedEntity: BreedEntity)

    @Query("DELETE FROM breed_table WHERE id = :id")
    suspend fun remove(id: String)
}