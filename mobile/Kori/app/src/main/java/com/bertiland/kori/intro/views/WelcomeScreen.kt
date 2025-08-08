package com.bertiland.kori.intro.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bertiland.kori.intro.controllers.IntroViewModel
import com.bertiland.kori.main.controllers.MainViewModel

@Composable
fun WelcomeScreen(navController: NavController) {
    WelcomeScreen(navController = navController, viewModel = IntroViewModel())
}

@Composable
fun WelcomeScreen(navController: NavController, viewModel: IntroViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenue sur Kori", style = MaterialTheme.typography.h5)

        Spacer(Modifier.height(24.dp))

        Button(onClick = { viewModel.onLoginClick(navController) }) {
            Text("Connexion")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(onClick = { viewModel.onSignupClick(navController) }) {
            Text("Créer un compte")
        }
    }
}
