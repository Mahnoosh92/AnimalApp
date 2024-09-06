package com.example.animalapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animalapp.data.common.DEFAULT_BREED_ID
import com.example.animalapp.data.common.SECONDARY_BREED_ID

@Entity(
    tableName = "breed_table"
)
data class BreedEntity(
    @ColumnInfo("description") val description: String?,
    @PrimaryKey val id: String,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false,
) {
    companion object {
        val DEFAULT = BreedEntity(
            description = "",
            id = DEFAULT_BREED_ID,
            name = ""
        )
        val SECONDARY = BreedEntity(
            description = "",
            id = SECONDARY_BREED_ID,
            name = ""
        )
    }
}
