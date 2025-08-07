package com.bertiland.kori.main.views.menus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bertiland.kori.R
import com.bertiland.kori.main.controllers.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun CustomTopAppBar(
    viewModel: MainViewModel,
    topBarTitle: MutableState<String>,
    isSearchVisible: Boolean,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    onSearchToggle: () -> Unit
) {
    var isScrollable by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    if (isSearchVisible) {
        // Afficher la barre de recherche
        SearchBar(
            viewModel = viewModel,
            onSearchQueryChanged = { query -> },
            onSearchClose = { onSearchToggle() } // Fermer la barre de recherche
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = topBarTitle.value,
                    color = Color.White,
                    fontSize = 20.sp,
                    maxLines = if (isScrollable) Int.MAX_VALUE else 1,
                    overflow = if (isScrollable) TextOverflow.Clip else TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isScrollable = !isScrollable }
                        .then(
                            if (isScrollable) Modifier.horizontalScroll(scrollState)
                            else Modifier
                        )
                )
            },
            backgroundColor = colorResource(id = R.color.primary),
            navigationIcon = {
                IconButton(onClick = {
                    // Ouvrir le menu
                    scope.launch { scaffoldState.drawerState.open() }
                }) {
                    Icon(Icons.Default.Menu, tint = Color.White, contentDescription = "Menu")
                }
            },
            actions = {
                IconButton(onClick = onSearchToggle) {
                    Icon(Icons.Default.Search, tint = Color.White, contentDescription = "Search")
                }
            },
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
