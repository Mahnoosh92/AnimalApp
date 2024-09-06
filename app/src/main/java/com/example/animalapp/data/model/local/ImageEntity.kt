package com.example.animalapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animalapp.data.common.DEFAULT_BREED_ID
import com.example.animalapp.data.common.DEFAULT_IMAGE_ID
import com.example.animalapp.data.common.SECONDARY_BREED_ID
import com.example.animalapp.data.common.SECONDARY_IMAGE_ID

@Entity(
    tableName = "image_table", foreignKeys = [
        androidx.room.ForeignKey(
            BreedEntity::class,
            parentColumns = ["id"],
            childColumns = ["breed_id"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )]
)
data class ImageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "height") val height: Int?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "breed_id") val breedId: String,
    @ColumnInfo(name = "width") val width: Int?
) {
    companion object {
        val DEFAULT = ImageEntity(
            id = DEFAULT_IMAGE_ID,
            height = 0,
            url = "",
            breedId = DEFAULT_BREED_ID,
            width = 0
        )
        val SECONDARY = ImageEntity(
            id = SECONDARY_IMAGE_ID,
            height = 0,
            url = "",
            breedId = SECONDARY_BREED_ID,
            width = 0
        )
    }
}