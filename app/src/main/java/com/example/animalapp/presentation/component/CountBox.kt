package com.example.animalapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.ui.theme.AnimalAppTheme

@Composable
fun CountBox(
    breed: Breed,
    modifier: Modifier = Modifier,
    onAddNumberOfOrderClicked: (Breed) -> Unit,
    onRemoveNumberOfOrderClicked: (Breed) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .width(200.dp)
            .height(50.dp)
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onAddNumberOfOrderClicked(breed) }) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
            }
            Text(text = breed.numberOfOrders.toString())
            IconButton(onClick = { onRemoveNumberOfOrderClicked(breed) }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun CountBoxPreview() {
    AnimalAppTheme {
        CountBox(
            breed = Breed(
                description = "some description",
                id = "",
                name = "name",
                isFavorite = false,
                numberOfOrders = 0,
                isAddedToCart = false,
                imageUrl = ""
            ),
            onAddNumberOfOrderClicked = {},
            onRemoveNumberOfOrderClicked = {})
    }
}