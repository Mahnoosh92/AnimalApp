package com.example.animalapp.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animalapp.R
import com.example.animalapp.presentation.component.AnimalItem

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onErrorReceived: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentSearchQuery by viewModel.breedQueryState.collectAsState()

    uiState.error?.let {
        onErrorReceived(it)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = currentSearchQuery,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { query ->
                    viewModel.onSearchValueChanged(query)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            )
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (uiState.breeds.isNotEmpty() && !uiState.isLoading) {
                LazyColumn {
                    items(uiState.breeds, key = { it.id }) {
                        AnimalItem(
                            breed = it,
                            onItemClicked = {},
                            onFavoriteClicked = {},
                            onAddNumberOfOrderClicked = {},
                            onRemoveNumberOfOrderClicked = {},
                            onAddToCartClicked = {},
                            onRemoveFromCartClicked = {})
                    }
                }
            }
            if (uiState.breeds.isEmpty() && !uiState.isLoading) {
                Text(text = stringResource(R.string.no_breeds_found))
            }
        }
    }
}