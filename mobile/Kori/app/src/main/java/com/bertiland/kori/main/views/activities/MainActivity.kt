package com.bertiland.kori.main.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import api.ttt.orm.ms.ext.onModelChange
import com.bertiland.kori.common.models.IntroState
import com.bertiland.kori.main.controllers.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        systemConfig(this)

        val vm = MainViewModel()

        setContent {
                MyApp(viewModel = vm)
        }
    }
}