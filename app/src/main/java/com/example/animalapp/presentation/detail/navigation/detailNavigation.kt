package com.example.animalapp.presentation.detail.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animalapp.presentation.detail.DetailScreen

const val DETAIL_ROUTE = "detail/{name}/{description}/{imageUrl}"

fun NavGraphBuilder.detail(modifier: Modifier = Modifier) {
    composable(
        DETAIL_ROUTE,
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("description") { type = NavType.StringType },
            navArgument("imageUrl") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name")
        val description = backStackEntry.arguments?.getString("description")
        val imageUrl = backStackEntry.arguments?.getString("imageUrl")
        DetailScreen(
            name = name,
            description = description,
            imageUrl = imageUrl,
            modifier = modifier
        )
    }

}