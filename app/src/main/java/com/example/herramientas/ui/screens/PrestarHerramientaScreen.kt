package com.example.herramientas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrestarHerramientaScreen(navController: NavController) {
    var nombreHerramienta by remember { mutableStateOf("") }
    var nombrePrestamo by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Prestar Herramienta",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = nombreHerramienta,
            onValueChange = { nombreHerramienta = it },
            label = { Text("Nombre de la herramienta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            // 👉 Aquí puedes implementar la búsqueda o mostrar un mensaje temporal
            println("Buscando herramienta: $nombreHerramienta")
        }) {
            Text("Buscar herramienta")
        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = nombrePrestamo,
            onValueChange = { nombrePrestamo = it },
            label = { Text("Nombre de quien la recibe") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = notas,
            onValueChange = { notas = it },
            label = { Text("Notas") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(onClick = {
            if (nombreHerramienta.isNotBlank() && nombrePrestamo.isNotBlank()) {
                println("Guardando préstamo de $nombreHerramienta a $nombrePrestamo con nota: $notas")
                // 👉 Aquí puedes guardar el préstamo o navegar a otra pantalla si quieres
                // navController.navigate(NavRoutes.VerHerramientaScreen.route)
            } else {
                println("⚠️ Faltan campos obligatorios")
            }
        }) {
            Text("Guardar préstamo")
        }
    }
}
