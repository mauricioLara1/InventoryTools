package com.example.herramientas.ui.navigation

sealed class NavRoutes(val route: String) {
    object MainScreen : NavRoutes("main_screen")
    object RegistrarHerramientaScreen : NavRoutes("registrar_herramienta_screen")
    object PrestarHerramientaScreen : NavRoutes("prestar_herramienta_screen")
    object VerHerramientaScreen : NavRoutes("ver_herramienta_screen")
}
