package com.example.herramientas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.herramientas.ui.screens.MainScreen
import com.example.herramientas.ui.screens.PrestarHerramientaScreen
import com.example.herramientas.ui.screens.RegistrarHerramientaScreen
import com.example.herramientas.ui.screens.VerHerramientaScreen
import com.example.herramientas.viewmodel.TestViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: TestViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MainScreen.route
    ) {
        composable(NavRoutes.MainScreen.route) {
            MainScreen(navController)
        }

        composable(NavRoutes.RegistrarHerramientaScreen.route) {
            RegistrarHerramientaScreen(
                navController = navController,
                agregarHerramienta = { herramienta -> viewModel.agregarHerramienta(herramienta) }
            )
        }

        composable(NavRoutes.PrestarHerramientaScreen.route) {
            PrestarHerramientaScreen(navController)
        }

        composable(NavRoutes.VerHerramientaScreen.route) {
            VerHerramientaScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
