package com.example.animalapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.animalapp.data.model.local.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Transaction
    @Query("SELECT * FROM breed_table")
    fun getBreedsWithImages(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breed_table")
    fun getBreeds(): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breedEntity: BreedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breedEntities: List<BreedEntity>)

    @Query("UPDATE breed_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun update(id: String, isFavorite: Int)

    @Query("DELETE FROM breed_table")
    suspend fun removeAll()

    @Query("DELETE FROM breed_table where id=:id")
    suspend fun remove(id:String)
}