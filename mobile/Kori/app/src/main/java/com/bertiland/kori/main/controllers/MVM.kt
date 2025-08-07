package com.bertiland.kori.main.controllers

import androidx.lifecycle.ViewModel
import com.bertiland.kori.main.models.ProjectSection
import com.bertiland.kori.main.models.User

class MVM : ViewModel() {
    val user = User()
    val sections = listOf(
        ProjectSection("Introduction"),
        ProjectSection("Objectifs"),
        ProjectSection("Architecture"),
        ProjectSection("Modules"),
        ProjectSection("Tests"),
        ProjectSection("Déploiement"),
    )
}
