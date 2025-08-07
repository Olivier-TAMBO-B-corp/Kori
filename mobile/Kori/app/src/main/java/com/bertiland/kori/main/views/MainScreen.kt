package com.bertiland.kori.main.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bertiland.kori.main.controllers.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val scaffoldState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = scaffoldState,
        drawerContent = {
            DrawerContent(
                user = viewModel.user,
                sections = viewModel.sections,
                onSectionClick = { /* TODO */ }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Kori") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // Screen content goes here
            }
        }
    }

    // Furtive auto-close logic
    LaunchedEffect(scaffoldState.isOpen) {
        if (scaffoldState.isOpen) {
            delay(5000)
            scaffoldState.close()
        }
    }
}
