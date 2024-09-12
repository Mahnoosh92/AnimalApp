package com.example.animalapp.presentation.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animalapp.presentation.home.homeMainList

@Composable
fun FilterScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: FilterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val lazyListState = rememberLazyListState()

    val shouldStartPaginate by remember {
        derivedStateOf {
            (lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -4) >= lazyListState.layoutInfo.totalItemsCount - 2
        }
    }
    LaunchedEffect(shouldStartPaginate) {
        if (shouldStartPaginate)
            viewModel.getMoreBreeds()
    }

    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is FilterUiState.Failure -> {
            Text(text = (uiState as FilterUiState.Failure).error)
        }

        FilterUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }

        is FilterUiState.BreedsList -> {
            if ((uiState as FilterUiState.BreedsList).breeds.isNotEmpty()) {
                homeMainList(
                    breeds = (uiState as FilterUiState.BreedsList).breeds,
                    lazyListState = lazyListState,
                    onItemClicked = {},
                    onFavoriteClicked = {},
                    onAddNumberOfOrderClicked = {},
                    onRemoveNumberOfOrderClicked = {},
                    onAddToCartClicked = {}
                ) {

                }
            }
        }

    }
}