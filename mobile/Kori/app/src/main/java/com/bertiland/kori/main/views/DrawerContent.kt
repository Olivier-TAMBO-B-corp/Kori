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
import com.bertiland.kori.main.models.ProjectSection
import com.bertiland.kori.main.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun DrawerContent(
    user: User,
    sections: List<ProjectSection>,
    onSectionClick: (ProjectSection) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // User info
        Text("Hello, ${user.name}", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp))

        // Project sections
        sections.forEach { section ->
            Text(
                text = section.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSectionClick(section) }
                    .padding(vertical = 8.dp)
            )
        }
    }
}
