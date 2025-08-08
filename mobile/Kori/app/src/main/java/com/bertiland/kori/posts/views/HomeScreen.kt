package com.bertiland.kori.posts.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.bertiland.kori.posts.controllers.GameViewModel

@Composable
fun HomeScreen(navController: NavController) {
    HomeScreen(navController = navController, viewModel = GameViewModel())
}

@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Empty
    }
}
