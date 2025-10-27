package com.example.herramientas.data.repository

import com.example.herramientas.data.db.dao.HerramientaDao
import com.example.herramientas.data.db.dao.PrestamoDao
import com.example.herramientas.data.model.HerramientaEntity
import com.example.herramientas.data.model.PrestamoEntity

/**
 * el repository centraliza toda la lógica de acceso a datos
 */
class InventarioRepository(private val herramientaDao: HerramientaDao, private val prestamoDao: PrestamoDao) {
    //funciones de herramientas
    suspend fun getHerramientas()=
        herramientaDao.getAll()
    suspend fun getHerramientaById(id: Int) =
        herramientaDao.getById(id)
    suspend fun buscarPorNombre(nombre: String) =
        herramientaDao.buscarPorNombre(nombre)
    suspend fun buscarPorPrestada(prestada: Boolean) =
        herramientaDao.buscarPorPrestada(prestada)

    suspend fun buscarPorNombreYPrestada(nombre: String, prestada: Boolean) =
        herramientaDao.buscarPorNombreYPrestada(nombre, prestada)

    suspend fun addHerramienta(herramienta: HerramientaEntity) {
        herramientaDao.insert(herramienta)
    }
    suspend fun marcarComoPrestada(id: Int){
        herramientaDao.marcarComoPrestada(id)
    }
    suspend fun marcarComoDevuelta(id: Int){
        herramientaDao.marcarComoDevuelta(id)
    }
    suspend fun actualizarCantidad(id: Int, cantidad: Int){
        herramientaDao.actualizarCantidad(id, cantidad)
    }
    suspend fun actualizarEstadoDevuelta(id: Int, prestada: Boolean) {
        herramientaDao.marcarComoDevuelta(id)
    }

    //funciones de prestamos

    suspend fun getPrestamos() =
        prestamoDao.getAll()
    suspend fun getPrestamoById(id: Int) =
        prestamoDao.getById(id)
    suspend fun addPrestamo(prestamo: PrestamoEntity) {
        prestamoDao.insert(prestamo)
        herramientaDao.marcarComoPrestada(prestamo.herramienta_id)
    }
    suspend fun buscarPorHerramientaId(herramientaId: Int)=
        prestamoDao.buscarPorHerramientaId(herramientaId)

}
