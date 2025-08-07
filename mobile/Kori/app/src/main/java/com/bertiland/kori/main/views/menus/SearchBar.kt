package com.bertiland.kori.main.views.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bertiland.kori.R
import com.bertiland.kori.main.controllers.MainViewModel


@Composable
fun SearchBar(
    viewModel: MainViewModel,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClose: () -> Unit
) {
    var searchQuery by remember { viewModel.searchQuery }

    Box (
        modifier = Modifier
            .background(colorResource(id = R.color.primary))
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    ///viewModel.searchQuery.value = it
                    onSearchQueryChanged(it)
                },
                placeholder = {
                    Text(
                        "Rechercher...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            IconButton(
                onClick = onSearchClose,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                Icon(Icons.Default.Search, tint = Color.White, contentDescription = "Search")
            }
        }
    }
}