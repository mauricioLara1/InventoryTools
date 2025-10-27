package com.example.herramientas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.activity.viewModels

import com.example.herramientas.ui.navigation.AppNavHost
import com.example.herramientas.ui.theme.HerramientasTheme
import com.example.herramientas.viewmodel.TestViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HerramientasTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController,viewModel = viewModel)
                }
            }
        }
    }
}
