package com.example.animalapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.example.animalapp.data.extensions.extractName
import com.example.animalapp.presentation.component.AnimalAppBottomBar
import com.example.animalapp.presentation.component.AnimalAppTopBar
import com.example.animalapp.presentation.detail.navigation.detail
import com.example.animalapp.presentation.filter.navigation.filter
import com.example.animalapp.presentation.home.navigation.HOME_ROUTE
import com.example.animalapp.presentation.home.navigation.home
import com.example.animalapp.presentation.search.navigation.search
import kotlinx.coroutines.launch

@Composable
fun AnimalAppGraph(
    state: AnimalAppState,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            AnimalAppTopBar(
                title = state.title,
                isBackButtonShown = !state.isTopLevelDestination
            ) {
                state.navController.navigateUp()
            }
        },
        bottomBar = {
            if (state.isTopLevelDestination)
                state.currentDestination?.route?.let {
                    AnimalAppBottomBar(
                        currentRoute = it,
                        destinations = state.getTopLevelDestinations()
                    ) { destination ->
                        state.navigateToTopLevelDestination(destination)
                    }
                }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddings ->
        NavHost(
            navController = state.navController,
            startDestination = HOME_ROUTE,
            modifier = modifier.padding(paddings)
        ) {
            home(
                snackbarHostState = snackbarHostState,
                onItemClicked = { breed ->
                    state.navController.navigate("detail/${breed.name}/${breed.description}/${breed.extractName()}")
                })
            search() {
                state.coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = it)
                }
            }
            filter(snackbarHostState = snackbarHostState)
            detail()
        }
    }
}