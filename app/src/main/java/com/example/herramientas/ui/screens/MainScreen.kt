package com.example.herramientas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.herramientas.ui.navigation.NavRoutes

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🔧 Herramientas App", modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = { navController.navigate(NavRoutes.RegistrarHerramientaScreen.route) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Registrar herramienta")
        }

        Button(
            onClick = { navController.navigate(NavRoutes.PrestarHerramientaScreen.route) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Prestar herramienta")
        }

        Button(
            onClick = { navController.navigate(NavRoutes.VerHerramientaScreen.route) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Ver herramientas")
        }
    }
}

