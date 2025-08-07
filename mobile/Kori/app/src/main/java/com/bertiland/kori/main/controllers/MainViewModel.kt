package com.bertiland.kori.main.controllers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.bertiland.kori.R
import com.bertiland.kori.common.viewmodels.TViewModel

class MainViewModel : TViewModel() {
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