package com.example.herramientas.data.repository

import com.example.herramientas.data.db.dao.HerramientaDao
import com.example.herramientas.data.db.dao.PrestamoDao
import com.example.herramientas.data.model.HerramientaEntity
import com.example.herramientas.data.model.PrestamoEntity

class InventarioRepository(
    private val herramientaDao: HerramientaDao,
    private val prestamoDao: PrestamoDao
) {

    // 🔹 POST: agregar herramienta
    suspend fun addHerramienta(herramienta: HerramientaEntity) {
        herramientaDao.insert(herramienta)
    }

    // 🔹 POST: registrar préstamo y marcar herramienta como prestada
    suspend fun addPrestamo(prestamo: PrestamoEntity) {
        prestamoDao.insert(prestamo)
        herramientaDao.marcarComoPrestada(prestamo.herramienta_id)
    }

    // 🔹 GET: todas las herramientas
    suspend fun getHerramientas() = herramientaDao.getAll()

    // 🔹 GET: herramienta por ID
    suspend fun getHerramientaById(id: Int) = herramientaDao.getById(id)

    // 🔹 GET: todos los préstamos
    suspend fun getPrestamos() = prestamoDao.getAll()

    // 🔹 GET: préstamo por ID
    suspend fun getPrestamoById(id: Int) = prestamoDao.getById(id)

    // 🔹 PATCH: reducir cantidad (sin negativos)
    suspend fun reducirCantidadHerramienta(id: Int, cantidad: Int) {
        herramientaDao.reducirCantidad(id, cantidad)
    }

    // 🔹 PATCH: cambiar estado de “prestada”
    suspend fun actualizarEstadoDevuelta(id: Int, prestada: Boolean) {
        herramientaDao.marcarComoDevuelta(id)
    }
}
