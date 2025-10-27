package com.example.herramientas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.herramientas.data.db.AppDatabase
import com.example.herramientas.data.model.HerramientaEntity
import com.example.herramientas.data.model.PrestamoEntity
import com.example.herramientas.data.repository.InventarioRepository
import kotlinx.coroutines.launch

/**
 * ViewModel: capa intermedia entre la UI y el repositorio.
 * Aquí se ejecutan todas las operaciones con Room mediante corutinas.
 *
 * MVVM Flow:
 *   UI -> ViewModel -> Repository -> DAO -> DB
 */
class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = InventarioRepository(db.herramientaDao(), db.prestamoDao())

    // --- Herramientas ---
    private val _herramientas = MutableLiveData<List<HerramientaEntity>>()
    val herramientas: LiveData<List<HerramientaEntity>> = _herramientas

    //se crea otra variable para cuando se retorna solo un dato
    private val _herramienta = MutableLiveData<HerramientaEntity?>()
    val herramienta: LiveData<HerramientaEntity?> = _herramienta

    // ---funciones post---
    fun agregarHerramienta(herramienta: HerramientaEntity) {
        viewModelScope.launch {
            repository.addHerramienta(herramienta)
            cargarHerramientas() // refresca lista
        }
    }

    // ---funciones get---
    fun cargarHerramientas() {
        viewModelScope.launch {
            _herramientas.value = repository.getHerramientas()
        }
    }
    fun getHerramientaById(id: Int) {
        viewModelScope.launch {
            _herramienta.value = repository.getHerramientaById(id)
        }
    }
    fun buscarPorNombre(nombre: String) {
        viewModelScope.launch {
            _herramientas.value = repository.buscarPorNombre(nombre)
        }
    }
    fun buscarPorPrestada(prestada: Boolean){
        viewModelScope.launch {
            _herramientas.value = repository.buscarPorPrestada(prestada)
        }
    }
    fun buscarPorNombreYPrestada(nombre: String, prestada: Boolean){
        viewModelScope.launch {
            _herramientas.value = repository.buscarPorNombreYPrestada(nombre, prestada)
        }
    }

    // ---funciones patch---
    fun actualizarCantidad(id: Int, cantidad: Int) {
        viewModelScope.launch {
            repository.actualizarCantidad(id, cantidad)
            cargarHerramientas()
        }
    }
    fun marcarPrestada(id: Int) {
        viewModelScope.launch {
            repository.marcarComoPrestada(id)
            cargarHerramientas()
        }
    }
    fun marcarDevuelta(id: Int) {
        viewModelScope.launch {
            repository.marcarComoDevuelta(id)
            cargarHerramientas()
        }
    }
    //con esta manual mente selecciono si true or false
    fun actualizarEstadoDevuelta(id: Int, prestada: Boolean){
        viewModelScope.launch {
            repository.actualizarEstadoDevuelta(id, prestada)
            cargarHerramientas()
        }
    }


/* para actualizar toda una herramienta
    fun actualizarHerramienta(herramienta: HerramientaEntity) {
        viewModelScope.launch {
            repository.updateHerramienta(herramienta)
            cargarHerramientas()
        }
    }
*/



    // --- Préstamos ---
    private val _prestamos = MutableLiveData<List<PrestamoEntity>>()
    val prestamos: LiveData<List<PrestamoEntity>> = _prestamos
    private val _prestamo = MutableLiveData<PrestamoEntity?>()
    val prestamo: LiveData<PrestamoEntity?> = _prestamo
    fun cargarPrestamos() {
        viewModelScope.launch {
            _prestamos.value = repository.getPrestamos()
        }
    }

    fun registrarPrestamo(prestamo: PrestamoEntity) {
        viewModelScope.launch {
            repository.addPrestamo(prestamo)
            cargarPrestamos()
        }
    }
    fun getPrestamoById(id: Int){
        viewModelScope.launch {
            _prestamo.value = repository.getPrestamoById(id)
        }
    }
    fun buscarPorHerramientaId(herramientaId: Int){
        viewModelScope.launch {
            _prestamos.value = repository.buscarPorHerramientaId(herramientaId)
        }
    }
}
