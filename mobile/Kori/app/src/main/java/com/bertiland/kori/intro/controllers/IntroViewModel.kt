package com.bertiland.kori.intro.controllers

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.bertiland.kori.intro.models.IntroState
import com.bertiland.kori.main.controllers.MainViewModel

class IntroViewModel(val mainViewModel: MainViewModel?=null) : ViewModel() {

    private val _uiState = MutableStateFlow(IntroState())
    val uiState: StateFlow<IntroState> = _uiState

    fun onStart(navController: NavController) {
        viewModelScope.launch {
            delay(2000)
            _uiState.value = _uiState.value.copy(isLoading = false)
            navController.navigate("welcome") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    fun onLoginClick(navController: NavController) {
        //navController.navigate("login")
        mainViewModel?.onReady()
    }

    fun onSignupClick(navController: NavController) {
        //navController.navigate("signup")
        mainViewModel?.onReady()
    }
}
