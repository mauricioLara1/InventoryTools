package com.example.herramientas.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.herramientas.data.db.AppDatabase
import com.example.herramientas.data.model.HerramientaEntity
import com.example.herramientas.data.model.PrestamoEntity
import com.example.herramientas.data.repository.InventarioRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = InventarioRepository(db.herramientaDao(), db.prestamoDao())

    fun probarBaseDeDatos() = viewModelScope.launch {
        // 1️⃣ Crear herramienta
        val martillo = HerramientaEntity(nombre = "Martillo", cantidad_actual = 5, prestada = false)
        repository.addHerramienta(martillo)
        Log.d("ROOM_TEST", "✅ Herramienta agregada: $martillo")

        // 2️⃣ Listar herramientas
        val herramientas = repository.getHerramientas()
        Log.d("ROOM_TEST", "🔍 Herramientas: $herramientas")

        // 3️⃣ Crear préstamo
        val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val prestamo = PrestamoEntity(
            herramienta_id = herramientas.first().id,
            persona = "Juan Pérez",
            fecha = fecha,
            prestamo_activo = true,
            comentario = "Uso en taller"
        )
        repository.addPrestamo(prestamo)
        Log.d("ROOM_TEST", "✅ Préstamo agregado: $prestamo")

        // 4️⃣ Consultar préstamos
        val prestamos = repository.getPrestamos()
        Log.d("ROOM_TEST", "🔍 Préstamos: $prestamos")

        // 5️⃣ Reducir cantidad (por ejemplo restar 2)
        repository.reducirCantidadHerramienta(herramientas.first().id, 2)
        val actualizada = repository.getHerramientaById(herramientas.first().id)
        Log.d("ROOM_TEST", "📦 Herramienta actualizada: $actualizada")
    }
}
