package com.example.animalapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.animalapp.R
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.domain.model.BreedWithImage
import com.example.animalapp.domain.model.Image
import com.example.animalapp.ui.theme.AnimalAppTheme

@Composable
fun AnimalItem(
    breedWithImage: BreedWithImage,
    modifier: Modifier = Modifier,
    onItemClicked: (BreedWithImage) -> Unit,
    onFavoriteClicked: (BreedWithImage) -> Unit,
    onAddNumberOfOrderClicked: (BreedWithImage) -> Unit,
    onRemoveNumberOfOrderClicked: (BreedWithImage) -> Unit,
    onAddToCartClicked: (BreedWithImage) -> Unit,
    onRemoveFromCartClicked: (BreedWithImage) -> Unit
) {
    Card(
        modifier =
        modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClicked(breedWithImage) })
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(breedWithImage.image.url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = breedWithImage.breed.description,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(Color.Red)
                        .clip(RoundedCornerShape(6.dp, 6.dp, 0.dp, 0.dp))
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = breedWithImage.breed.name ?: "Sample one",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = breedWithImage.breed.description ?: "Sample one",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 16.sp,
                    maxLines = 4
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onRemoveFromCartClicked(breedWithImage) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { onAddToCartClicked(breedWithImage) }) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = if (breedWithImage.breed.isAddedToCart) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
                CountBox(
                    breedWithImage = breedWithImage,
                    onAddNumberOfOrderClicked = onAddNumberOfOrderClicked,
                    onRemoveNumberOfOrderClicked = onRemoveNumberOfOrderClicked
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { onFavoriteClicked(breedWithImage) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = if (breedWithImage.breed.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }

}


@Preview
@Composable
fun AnimalItemPreview() {
    AnimalAppTheme {
        AnimalItem(
            breedWithImage = BreedWithImage(
                breed = Breed(
                    description = "some description",
                    id = "",
                    name = "name",
                    isFavorite = false,
                    numberOfOrders = 0,
                    isAddedToCart = false
                ),
                image = Image(height = 0, id = "", url = "", width = 0),
            ),
            onItemClicked = {},
            onFavoriteClicked = {},
            onAddNumberOfOrderClicked = {},
            onRemoveNumberOfOrderClicked = {},
            onAddToCartClicked = {},
            onRemoveFromCartClicked = {}
        )
    }
}