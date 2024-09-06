package com.example.animalapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animalapp.presentation.detail.navigation.DETAIL_ROUTE
import com.example.animalapp.presentation.filter.navigation.FILTER_ROUTE
import com.example.animalapp.presentation.home.navigation.HOME_ROUTE
import com.example.animalapp.presentation.navigation.AnimalAppScreen
import com.example.animalapp.presentation.search.navigation.SEARCH_ROUTE
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAnimalAppState(
    navController: NavHostController,
    coroutineScope: CoroutineScope
): AnimalAppState = remember(navController, coroutineScope) {
    AnimalAppState(
        navController = navController,
        coroutineScope = coroutineScope
    )
}

class AnimalAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun getTopLevelDestinations() = AnimalAppScreen.entries.filter { it.isTopLevel }

    fun navigateToTopLevelDestination(destination: AnimalAppScreen) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val isTopLevelDestination: Boolean
        @Composable get() = when (currentDestination?.route) {
            HOME_ROUTE, SEARCH_ROUTE, FILTER_ROUTE -> true
            else -> false
        }
    val title: String
        @Composable get() = when (currentDestination?.route) {
            HOME_ROUTE -> stringResource(id = AnimalAppScreen.HOME.titleText)
            SEARCH_ROUTE -> stringResource(id = AnimalAppScreen.SEARCH.titleText)
            FILTER_ROUTE -> stringResource(id = AnimalAppScreen.FILTER.titleText)
            DETAIL_ROUTE -> stringResource(id = AnimalAppScreen.DETAILS.titleText)
            else -> ""
        }
}