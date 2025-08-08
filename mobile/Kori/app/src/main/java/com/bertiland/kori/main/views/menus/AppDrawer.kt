package com.bertiland.kori.main.views.menus


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bertiland.kori.common.viewmodels.TViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavController,
    viewModel: TViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            HeaderSection(
                name = "User Name",
                key = "- - -",
                photo = "",
                objects = 714,
                reports = 0,
                models = 134
            )
        }

        item {
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            NavigationItem(icon = Icons.Default.Home, label = "Home") { // feed, posts, games parties
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.History, label = "My Space") { // user content
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.GpsFixed, label = "Map") { // User Network
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.Games, label = "Games") { // Games Models
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.Abc, label = "Dico") { // Dictionary
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            Text(
                text = "Spoozy",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        item {
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item {
            NavigationItem(icon = Icons.Default.EventNote, label = "Pay") {
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.EventNote, label = "Eventa") {
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.EventNote, label = "FindMe") {
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }
        item {
            NavigationItem(icon = Icons.Default.EventNote, label = "IM") {
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Déclaration de Confidentialité",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        item {
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        item {
            NavigationItem(icon = Icons.Default.Security, label = "Ouvrir") {
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            NavigationItem(icon = Icons.Default.Settings, label = "Paramètres") { // user settings
                defaultScreen(navController, viewModel, scope, scaffoldState)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

fun defaultScreen(
    navController: NavController,
    viewModel: TViewModel,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    navController.navigate("home")
    scope.launch { scaffoldState.drawerState.close() }
}
