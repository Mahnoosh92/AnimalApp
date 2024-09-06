package com.example.animalapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.animalapp.ui.theme.AnimalAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state = rememberAnimalAppState(
                navController = rememberNavController(),
                coroutineScope = rememberCoroutineScope()
            )
            AnimalAppTheme {
                AnimalAppGraph(
                    state = state,
                )
            }
        }
    }
}

