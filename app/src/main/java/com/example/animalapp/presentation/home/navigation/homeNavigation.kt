package com.example.animalapp.presentation.home.navigation

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.presentation.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavGraphBuilder.home(modifier: Modifier = Modifier, onAnimalItemClicked: () -> Unit) {
    composable(HOME_ROUTE) {
        HomeScreen(modifier = modifier, onAnimalItemClicked = onAnimalItemClicked)
    }
}