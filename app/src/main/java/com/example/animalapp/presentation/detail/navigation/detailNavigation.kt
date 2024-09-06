package com.example.animalapp.presentation.detail.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.animalapp.presentation.detail.DetailScreen

const val DETAIL_ROUTE = "detail"

fun NavGraphBuilder.detail(modifier: Modifier = Modifier) {
    composable(DETAIL_ROUTE) {
        DetailScreen(modifier = modifier)
    }

}