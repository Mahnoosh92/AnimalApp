package com.example.animalapp.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.animalapp.presentation.navigation.AnimalAppScreen


@Composable
fun AnimalAppBottomBar(
    currentRoute: String,
    destinations: List<AnimalAppScreen>,
    modifier: Modifier = Modifier,
    onBottomItemClicked: (AnimalAppScreen) -> Unit
) {
    NavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val isSelected = currentRoute == destination.route
            NavigationBarItem(
                selected = isSelected,
                icon = {
                    if (destination.selectedIcon != null && destination.unselectedIcon != null)
                        Icon(
                            imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = null
                        )
                },
                label = {
                    Text(text = stringResource(id = destination.titleText))
                },
                onClick = {
                    onBottomItemClicked(destination)
                }
            )
        }
    }
}