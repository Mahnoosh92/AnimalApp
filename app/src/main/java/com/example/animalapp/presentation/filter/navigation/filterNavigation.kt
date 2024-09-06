package com.example.animalapp.presentation.filter.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.presentation.filter.FilterScreen

const val FILTER_ROUTE = "filter"

fun NavGraphBuilder.filter(modifier: Modifier = Modifier) {
    composable(FILTER_ROUTE) {
        FilterScreen(modifier = modifier)
    }
}