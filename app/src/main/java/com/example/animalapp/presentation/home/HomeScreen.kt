package com.example.animalapp.presentation.home

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onAnimalItemClicked: () -> Unit) {
    TextButton(modifier = modifier, onClick = { onAnimalItemClicked() }) {
        Text(text = "home")
    }
}