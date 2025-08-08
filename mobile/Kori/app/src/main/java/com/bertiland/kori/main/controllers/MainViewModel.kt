package com.bertiland.kori.main.controllers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import api.ttt.orm.ms.ext.all
import api.ttt.orm.ms.ext.isNotEmpty
import api.ttt.orm.ms.ext.onModelChange
import com.bertiland.kori.common.viewmodels.TViewModel
import com.bertiland.kori.common.models.IntroState

class MainViewModel : TViewModel() {
    init {
        initObservables()
    }

    private fun initObservables() {
        IntroState.tms.onModelChange { keys ->
            //
        }
    }

    fun onBackPressed(){}

    var colorPrimaryDark: Color = Color(0)
    var colorPrimary: Color = Color(0)

    var topBarTitle =  mutableStateOf("Kori")

    var statusBarColor =  mutableStateOf(Color.White)
    var navigationBarColor =  mutableStateOf(Color.White)
    var isTopBarVisible =  mutableStateOf(false)

    var searchQuery =  mutableStateOf("")

    fun onReady() {
        statusBarColor.value = colorPrimary
        navigationBarColor.value = colorPrimary
        isTopBarVisible.value = true
    }
}