package com.example.herramientas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


import com.example.herramientas.data.model.PrestamoEntity

import com.example.herramientas.viewmodel.TestViewModel

@Composable
fun VerHerramientaScreen(
    navController: NavController,
    viewModel: TestViewModel // tu ViewModel que maneja herramientas y préstamos
) {
    var nombreHerramienta by remember { mutableStateOf("") }
    var filtroPrestada by remember { mutableStateOf<String?>("Ignorar") } // "Prestada", "No prestada", "Ignorar"
    var cantidadModificar by remember { mutableStateOf("") }
    var herramientasSeleccionadas by remember { mutableStateOf(setOf<Int>()) }

    val herramientas by viewModel.herramientas.observeAsState(initial = emptyList())
    val prestamos by viewModel.prestamos.observeAsState(initial = emptyList())

    //val prestamos by viewModel.prestamos.observeAsState<List<PrestamoEntity>>(initial = emptyList())
    //val herramientas by viewModel.herramientas.observeAsState<List<HerramientaEntity>>(initial = emptyList())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ver Herramientas", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        // Caja de texto para buscar por nombre
        OutlinedTextField(
            value = nombreHerramienta,
            onValueChange = { nombreHerramienta = it },
            label = { Text("Buscar herramienta por nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Filtro de prestada
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Filtrar por estado:", modifier = Modifier.padding(end = 8.dp))
            listOf("Ignorar", "Prestada", "No prestada").forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    RadioButton(
                        selected = filtroPrestada == option,
                        onClick = { filtroPrestada = option }
                    )
                    Text(option)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                // Buscar según condiciones
                when (filtroPrestada) {
                    "Prestada" -> viewModel.buscarPorNombreYPrestada(nombreHerramienta, true)
                    "No prestada" -> viewModel.buscarPorNombreYPrestada(nombreHerramienta, false)
                    "Ignorar" -> viewModel.buscarPorNombre(nombreHerramienta)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Buscar")
        }

        Spacer(Modifier.height(16.dp))

        // Tabla de resultados
        Text("🔧 Resultados:")
        Spacer(Modifier.height(8.dp))

        Column {
            herramientas.forEach { herramienta ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Checkbox(
                        checked = herramientasSeleccionadas.contains(herramienta.id),
                        onCheckedChange = { checked ->
                            herramientasSeleccionadas = if (checked) {
                                herramientasSeleccionadas + herramienta.id
                            } else {
                                herramientasSeleccionadas - herramienta.id
                            }
                        }
                    )
                    Text("${herramienta.nombre} (Cantidad: ${herramienta.cantidad_actual}, Prestada: ${herramienta.prestada})")
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        // Caja para modificar cantidad
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = cantidadModificar,
                onValueChange = { cantidadModificar = it },
                label = { Text("Cantidad (+/-)") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    val cantidad = cantidadModificar.toIntOrNull() ?: 0
                    herramientasSeleccionadas.forEach { id ->
                        viewModel.actualizarCantidad(id, cantidad)
                    }
                    cantidadModificar = ""
                    herramientasSeleccionadas = emptySet()
                }
            ) {
                Text("Actualizar")
            }
        }

        Spacer(Modifier.height(24.dp))

        // Historial de préstamos de la primera herramienta seleccionada
        val idSeleccionada = herramientasSeleccionadas.firstOrNull()
    }
}
