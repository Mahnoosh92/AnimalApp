package com.example.animalapp.presentation.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.presentation.search.SearchScreen

const val SEARCH_ROUTE = "search"

fun NavGraphBuilder.search(onErrorReceived: (String) -> Unit) {
    composable(SEARCH_ROUTE) {
        SearchScreen(onErrorReceived = onErrorReceived)
    }

}