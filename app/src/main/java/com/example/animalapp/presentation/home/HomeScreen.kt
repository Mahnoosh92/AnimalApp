package com.example.animalapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animalapp.domain.model.BreedWithImage
import com.example.animalapp.presentation.component.AnimalItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onErrorReceived: (String) -> Unit,
    onItemClicked: (BreedWithImage) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    uiState.error?.let {
        onErrorReceived(it)
    }
    val lazyListState = rememberLazyListState()

    val shouldStartPaginate by remember {
        derivedStateOf {
            (lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -4) >= lazyListState.layoutInfo.totalItemsCount - 2
        }
    }
    LaunchedEffect(shouldStartPaginate) {
        if (shouldStartPaginate)
            viewModel.onEvent(HomeUiEvent.LoadMoreBreeds)
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = uiState.numberOfItemsInCart.toString(), textAlign = TextAlign.End
            )
            if (uiState.isLoading && (uiState.breeds?.size == 0 || uiState.breeds == null)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.breeds?.let { breeds ->
                if (breeds.isNotEmpty() && !uiState.isLoading)
                    homeMainList(
                        breeds = breeds,
                        lazyListState = lazyListState,
                        onItemClicked = { breed ->
                            onItemClicked(breed)
                        },
                        onFavoriteClicked = { breed ->
                            viewModel.onEvent(HomeUiEvent.OnFavouriteClicked(breed))
                        },
                        onAddNumberOfOrderClicked = { breed ->
                            viewModel.onEvent(HomeUiEvent.OnAddNumberOfOrdersClicked(breed))
                        },
                        onRemoveNumberOfOrderClicked = { breed ->
                            viewModel.onEvent(HomeUiEvent.OnRemoveNumberOfOrdersClicked(breed))
                        },
                        onAddToCartClicked = { breed ->
                            viewModel.onEvent(HomeUiEvent.OnAddToCartClicked(breed))
                        },
                        onRemoveFromCartClicked = { breed ->
                            viewModel.onEvent(HomeUiEvent.OnRemoveFromCartClicked(breed))
                        }
                    )
            }
        }
    }
}

@Composable
fun homeMainList(
    breeds: List<BreedWithImage>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (BreedWithImage) -> Unit,
    onFavoriteClicked: (BreedWithImage) -> Unit,
    onAddNumberOfOrderClicked: (BreedWithImage) -> Unit,
    onRemoveNumberOfOrderClicked: (BreedWithImage) -> Unit,
    onAddToCartClicked: (BreedWithImage) -> Unit,
    onRemoveFromCartClicked: (BreedWithImage) -> Unit,
) {
    LazyColumn(state = lazyListState, modifier = modifier.fillMaxWidth()) {
        items(breeds, key = { it.breed.id }) { breed ->
            AnimalItem(
                breedWithImage = breed,
                onItemClicked = { onItemClicked(breed) },
                onFavoriteClicked = { onFavoriteClicked(breed) },
                onAddNumberOfOrderClicked = { onAddNumberOfOrderClicked(breed) },
                onRemoveNumberOfOrderClicked = { onRemoveNumberOfOrderClicked(breed) },
                onAddToCartClicked = { onAddToCartClicked(breed) },
                onRemoveFromCartClicked = { onRemoveFromCartClicked(breed) }
            )
        }
    }
}
