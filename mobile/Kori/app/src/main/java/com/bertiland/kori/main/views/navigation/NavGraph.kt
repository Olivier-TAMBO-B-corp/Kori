package com.bertiland.kori.main.views.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bertiland.kori.intro.controllers.IntroViewModel
import com.bertiland.kori.intro.views.SplashScreen
import com.bertiland.kori.intro.views.WelcomeScreen
import com.bertiland.kori.main.controllers.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    isTopBarVisible: MutableState<Boolean>,
    viewModel: MainViewModel,
    startDestination : String,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination, Modifier.padding(paddingValues)
    ) {
        composable("post/{id}") {backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            isTopBarVisible.value = true
        }
        composable("word/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            isTopBarVisible.value = true
        }
        composable("users/login") {  }

        composable("users/signup") {  }

        composable("splash") { SplashScreen(navController, viewModel = IntroViewModel(mainViewModel = viewModel)) }

        composable("welcome") { WelcomeScreen(navController, viewModel = IntroViewModel(mainViewModel = viewModel)) }
    }
}
