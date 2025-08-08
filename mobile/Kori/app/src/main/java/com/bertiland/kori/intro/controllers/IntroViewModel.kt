package com.bertiland.kori.intro.controllers

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import api.ttt.orm.ms.ext.all
import api.ttt.orm.ms.ext.isNotEmpty
import com.bertiland.kori.common.models.IntroState

class IntroViewModel() : ViewModel() {
    private var introState: IntroState = IntroState()

    init {
        if(IntroState.tms.isNotEmpty()){
            introState = IntroState.tms.all().first()
        }

        introState.isLoading_ = true
        introState.onSignup_ = false
        introState.onLogin_ = false
        introState.save()
    }

    var isIntroLoading =  mutableStateOf(introState.isLoading_)

    private val _uiState = MutableStateFlow(introState)
    val uiState: StateFlow<IntroState> = _uiState

    fun onStart(navController: NavController) {
        /** run system init process
        */

        viewModelScope.launch {
            delay(2000)

            introState.isLoading_ = false
            isIntroLoading.value = introState.isLoading_
            introState.save()

            navController.navigate("welcome") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    fun onLoginClick(navController: NavController) {
        //navController.navigate("login")
        introState.onLogin_ = true
        introState.save()
    }

    fun onSignupClick(navController: NavController) {
        //navController.navigate("signup")
        introState.onSignup_ = true
        introState.save()
    }
}
