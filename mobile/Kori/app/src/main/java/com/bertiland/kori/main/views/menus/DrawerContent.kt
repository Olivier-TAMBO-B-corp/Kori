package com.bertiland.kori.main.views.menus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bertiland.kori.main.models.ProjectSection
import com.bertiland.kori.main.models.User


@Composable
fun DrawerContent(
    user: User,
    sections: List<ProjectSection>,
    onSectionClick: (ProjectSection) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // User info
        Text("Hello, User Name", style = MaterialTheme.typography.titleLarge)
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
