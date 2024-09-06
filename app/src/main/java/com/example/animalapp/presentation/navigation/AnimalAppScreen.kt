package com.example.animalapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.animalapp.R
import com.example.animalapp.presentation.detail.navigation.DETAIL_ROUTE
import com.example.animalapp.presentation.filter.navigation.FILTER_ROUTE
import com.example.animalapp.presentation.home.navigation.HOME_ROUTE
import com.example.animalapp.presentation.search.navigation.SEARCH_ROUTE

enum class AnimalAppScreen(
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    @StringRes val titleText: Int,
    val isTopLevel: Boolean = false,
    val route: String
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        R.string.home,
        isTopLevel = true,
        route = HOME_ROUTE
    ),
    SEARCH(
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        R.string.search,
        isTopLevel = true,
        route = SEARCH_ROUTE
    ),
    FILTER(
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        R.string.filter,
        isTopLevel = true,
        route = FILTER_ROUTE
    ),
    DETAILS(
        titleText = R.string.detail,
        isTopLevel = false,
        route = DETAIL_ROUTE
    )
}