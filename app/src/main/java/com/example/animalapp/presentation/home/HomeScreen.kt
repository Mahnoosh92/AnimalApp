package com.example.animalapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animalapp.data.common.UiConstants
import com.example.animalapp.domain.model.Breed
import com.example.animalapp.presentation.component.AnimalItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onErrorReceived: (String) -> Unit,
    onItemClicked: (Breed) -> Unit
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = uiState.numberOfItemsInCart.toString(), textAlign = TextAlign.End
                )
                Button(onClick = { viewModel.onEvent(HomeUiEvent.SaveChangedBreedsWithImage) }) {
                    Text(text = "Save")
                }
            }
            if (uiState.isLoading && (uiState.breeds.isEmpty())) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.testTag(UiConstants.UiTags.ProgressIndicator.customName))
                }
            }
            uiState.breeds.let { breeds ->
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
    breeds: List<Breed>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (Breed) -> Unit,
    onFavoriteClicked: (Breed) -> Unit,
    onAddNumberOfOrderClicked: (Breed) -> Unit,
    onRemoveNumberOfOrderClicked: (Breed) -> Unit,
    onAddToCartClicked: (Breed) -> Unit,
    onRemoveFromCartClicked: (Breed) -> Unit,
) {
    LazyColumn(state = lazyListState, modifier = modifier.fillMaxWidth().testTag(UiConstants.UiTags.BreedsLazyList.customName)) {
        items(breeds, key = { it.id }) { breed ->
            AnimalItem(
                breed = breed,
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
