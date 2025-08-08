package com.bertiland.kori.main.views.activities

import android.content.Context
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.bertiland.kori.R
import com.bertiland.kori.main.controllers.MainViewModel
import com.bertiland.kori.main.views.menus.AppDrawer
import com.bertiland.kori.main.views.menus.CustomTopAppBar
import com.bertiland.kori.main.views.navigation.NavGraph
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MyApp(viewModel: MainViewModel) {
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isSearchVisible by remember { mutableStateOf(false) }

    viewModel.navController(navController)

    viewModel.colorPrimary = colorResource(id = R.color.primary)
    viewModel.colorPrimaryDark = colorResource(id = R.color.primary_dark)

    // Configurer la transparence des barres système
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = viewModel.statusBarColor.value, // Transparente
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = viewModel.navigationBarColor.value,
            darkIcons = true
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->

        }
    }

    LaunchedEffect(viewModel.statusBarColor.value, viewModel.navigationBarColor.value) {
        systemUiController.setStatusBarColor(
            color = viewModel.statusBarColor.value,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = viewModel.navigationBarColor.value,
            darkIcons = true
        )
    }

    // Fond global appliqué à toute l'application
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .systemBarsPadding()
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = { AppDrawer(navController, viewModel, scaffoldState, scope) },
            topBar = {
                if (viewModel.isTopBarVisible.value) {
                    CustomTopAppBar(
                        viewModel = viewModel,
                        topBarTitle = viewModel.topBarTitle,
                        isSearchVisible = isSearchVisible,
                        scaffoldState = scaffoldState,
                        scope = scope
                    ) {
                        isSearchVisible = !isSearchVisible
                    }
                }
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Prendre en compte le padding du Scaffold
                ) {
                    NavGraph(
                        navController = navController,
                        paddingValues = paddingValues,
                        viewModel.isTopBarVisible,
                        viewModel = viewModel,
                        startDestination = "splash"
                    )
                }
            }
        )
    }
}

fun handleBackPress(
    viewModel: MainViewModel,
    context: Context,
    backPressedDispatcher: OnBackPressedDispatcher?
) {
    // Ajouter un callback pour le bouton retour
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Logique personnalisée
            //Toast.makeText(context, "Back button pressed inside Composable!", Toast.LENGTH_SHORT).show()
            viewModel.onBackPressed()

            // Désactiver le callback pour laisser le système gérer le reste
            isEnabled = false
            backPressedDispatcher?.onBackPressed()
        }
    }

    // Enregistrer le callback
    backPressedDispatcher?.addCallback(callback)
}


fun log(context: Context, i:String){
    Toast.makeText(context, i, Toast.LENGTH_SHORT).show()
}

fun systemConfig(activity : ComponentActivity) {
    // Configurer la fenêtre pour un affichage plein écran
    activity.window.statusBarColor = android.graphics.Color.TRANSPARENT // Transparente
    activity.window.navigationBarColor = android.graphics.Color.TRANSPARENT // Transparente
    // Empêche la vue parent de se redimensionner lorsque le clavier apparaît
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
}