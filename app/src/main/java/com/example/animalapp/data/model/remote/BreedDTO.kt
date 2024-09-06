package com.example.animalapp.data.model.remote

import com.example.animalapp.data.common.DEFAULT_BREED_ID
import com.example.animalapp.data.common.DEFAULT_IMAGE_ID
import com.example.animalapp.data.common.SECONDARY_BREED_ID
import com.example.animalapp.data.common.SECONDARY_IMAGE_ID
import com.google.gson.annotations.SerializedName

data class BreedDTO(
    @SerializedName("description") val description: String?,
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: ImageDTO,
    @SerializedName("name") val name: String?
) {
    companion object {
        val DEFAULT =
            BreedDTO(description = "", id = DEFAULT_BREED_ID, image = ImageDTO.DEFAULT, name = "")
        val SECONDARY =
            BreedDTO(description = "", id = SECONDARY_BREED_ID, image = ImageDTO.DEFAULT, name = "")
    }
}

data class ImageDTO(
    @SerializedName("height") val height: Int?,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String?,
    @SerializedName("width") val width: Int?
) {
    companion object {
        val DEFAULT = ImageDTO(height = 0, id = DEFAULT_IMAGE_ID, url = "", width = 0)
        val SECONDARY = ImageDTO(height = 0, id = SECONDARY_IMAGE_ID, url = "", width = 0)
    }
}
