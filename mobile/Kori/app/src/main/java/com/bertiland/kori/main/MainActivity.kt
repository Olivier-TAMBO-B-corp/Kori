package com.bertiland.kori.main

/*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bertiland.kori.intro.controllers.IntroViewModel
import com.bertiland.kori.intro.views.SplashScreen
import com.bertiland.kori.intro.views.WelcomeScreen
import com.bertiland.kori.main.views.DrawerItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
*/
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val introViewModel = remember { IntroViewModel() }

            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController, viewModel = introViewModel) }
                composable("welcome") { WelcomeScreen(navController, viewModel = introViewModel) }
            }
        }
    }
}*/
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val introViewModel = remember { IntroViewModel() }

            // Drawer content (menu avec les sections du projet, user info, etc.)
            val drawerContent: @Composable ColumnScope.() -> Unit = {
                Text("Nom d'utilisateur", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                Divider()
                DrawerItem("Accueil") { navController.navigate("home") }
                DrawerItem("Profil") { navController.navigate("profile") }
                DrawerItem("Paramètres") { navController.navigate("settings") }
                // Ajoute toutes les sections de ton projet ici...
            }

            // ModalNavigationDrawer (layout de base avec drawer)
            ModalNavigationDrawer(
                drawerState = drawerState, //lle seul pb maintenant requied material3... find material...
                drawerContent = {
                    ModalDrawerSheet {
                        drawerContent()
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Mon App") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                ) { padding ->
                    // Navigation
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("splash") {
                            SplashScreen(navController, viewModel = introViewModel)

                            // Ouvre drawer furtivement après retour arrière
                            BackHandler {
                                scope.launch {
                                    drawerState.open()
                                    delay(3000) // délai avant fermeture
                                    drawerState.close()
                                }
                            }
                        }

                        composable("welcome") {
                            WelcomeScreen(navController, viewModel = introViewModel)
                        }

                        composable("home") { Text("Page d'accueil") }
                        composable("profile") { Text("Page de profil") }
                        composable("settings") { Text("Page des paramètres") }
                    }
                }
            }
        }
    }
}
*/


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.bertiland.kori.intro.controllers.IntroViewModel
import com.bertiland.kori.intro.views.SplashScreen
import com.bertiland.kori.intro.views.WelcomeScreen
import com.bertiland.kori.main.views.DrawerItem
import com.bertiland.kori.ui.theme.KoriTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoriTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val introViewModel = remember { IntroViewModel() }

                // Drawer content
                val drawerContent: @Composable ColumnScope.() -> Unit = {
                    Text(
                        "Nom d'utilisateur",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Divider()
                    DrawerItem("Accueil") { navController.navigate("home") }
                    DrawerItem("Profil") { navController.navigate("profile") }
                    DrawerItem("Paramètres") { navController.navigate("settings") }
                }

                // Drawer + Scaffold + Navigation
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet { drawerContent() }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Kori") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        }
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = "splash",
                            modifier = Modifier.padding(padding)
                        ) {
                            composable("splash") {
                                SplashScreen(navController, viewModel = introViewModel)
                                BackHandler {
                                    scope.launch {
                                        drawerState.open()
                                        delay(3000)
                                        drawerState.close()
                                    }
                                }
                            }

                            composable("welcome") {
                                WelcomeScreen(navController, viewModel = introViewModel)
                            }

                            composable("home") {
                                Text("Page d'accueil", modifier = Modifier.padding(24.dp))
                            }

                            composable("profile") {
                                Text("Page de profil", modifier = Modifier.padding(24.dp))
                            }

                            composable("settings") {
                                Text("Page des paramètres", modifier = Modifier.padding(24.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
