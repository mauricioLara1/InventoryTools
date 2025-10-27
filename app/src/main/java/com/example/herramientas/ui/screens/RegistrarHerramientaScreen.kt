package com.example.herramientas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.herramientas.data.model.HerramientaEntity

@Composable
fun RegistrarHerramientaScreen(
    navController: NavController,
    agregarHerramienta: (HerramientaEntity) -> Unit // función del ViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var prestada by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registrar Herramienta",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        // 🔹 Nombre de la herramienta
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la herramienta") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // 🔹 Cantidad (solo números)
        OutlinedTextField(
            value = cantidad,
            onValueChange = { input ->
                // Filtramos: solo números positivos (sin letras ni signos)
                if (input.all { it.isDigit() }) {
                    cantidad = input
                }
            },
            label = { Text("Cantidad actual") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // 🔹 Checkbox: prestada o no
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = prestada,
                onCheckedChange = { prestada = it }
            )
            Text("¿Está prestada?")
        }

        Spacer(Modifier.height(24.dp))

        // 🔹 Botón registrar
        Button(
            onClick = {
                val cantidadInt = cantidad.toIntOrNull()

                // Validaciones básicas
                when {
                    nombre.isBlank() -> {
                        Toast.makeText(context, "Ingrese un nombre válido", Toast.LENGTH_SHORT).show()
                    }
                    cantidadInt == null -> {
                        Toast.makeText(context, "Ingrese una cantidad válida (solo números)", Toast.LENGTH_SHORT).show()
                    }
                    cantidadInt < 0 -> {
                        Toast.makeText(context, "La cantidad no puede ser negativa", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // ✅ Crear entidad y agregar
                        val herramienta = HerramientaEntity(
                            nombre = nombre.trim(),
                            cantidad_actual = cantidadInt,
                            prestada = prestada
                        )
                        agregarHerramienta(herramienta)

                        Toast.makeText(context, "Herramienta registrada", Toast.LENGTH_SHORT).show()

                        // Limpieza del formulario
                        nombre = ""
                        cantidad = ""
                        prestada = false

                        // Opcional: volver atrás
                        // navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}
