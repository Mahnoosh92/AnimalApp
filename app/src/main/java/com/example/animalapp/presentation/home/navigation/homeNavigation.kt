package com.example.animalapp.presentation.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.domain.model.BreedWithImage
import com.example.animalapp.presentation.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavGraphBuilder.home(
    modifier: Modifier = Modifier,
    onErrorReceived: (String) -> Unit,
    onItemClicked: (BreedWithImage) -> Unit
) {
    composable(HOME_ROUTE) {
        HomeScreen(
            modifier = modifier,
            onErrorReceived = onErrorReceived,
            onItemClicked = onItemClicked
        )
    }
}