package com.bertiland.kori.intro.views

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bertiland.kori.intro.controllers.IntroViewModel

@Composable
fun SplashScreen(navController: NavController, viewModel: IntroViewModel) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onStart(navController)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Kori",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "from Bertiland Corp",
                    style = MaterialTheme.typography.body1.copy(
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}
