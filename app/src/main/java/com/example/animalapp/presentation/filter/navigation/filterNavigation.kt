package com.example.animalapp.presentation.filter.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.presentation.filter.FilterScreen

const val FILTER_ROUTE = "filter"

fun NavGraphBuilder.filter(snackbarHostState: SnackbarHostState) {
    composable(FILTER_ROUTE) {
        FilterScreen(snackbarHostState = snackbarHostState)
    }
}