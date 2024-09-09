package com.example.animalapp.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.presentation.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavGraphBuilder.home(
    onErrorReceived: (String) -> Unit,
    onItemClicked: (Breed) -> Unit
) {
    composable(HOME_ROUTE) {
        HomeScreen(
            onErrorReceived = onErrorReceived,
            onItemClicked = onItemClicked
        )
    }
}