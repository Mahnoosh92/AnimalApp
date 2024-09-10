package com.example.animalapp.presentation.home.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.presentation.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavGraphBuilder.home(
    snackbarHostState: SnackbarHostState,
    onItemClicked: (Breed) -> Unit
) {
    composable(HOME_ROUTE) {
        HomeScreen(
            snackbarHostState = snackbarHostState,
            onItemClicked = onItemClicked
        )
    }
}