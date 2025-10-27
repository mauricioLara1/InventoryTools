package com.example.herramientas.data.db.dao

import com.example.herramientas.data.model.HerramientaEntity
import androidx.room.*

@Dao
interface HerramientaDao {
    //---funciones post---
    @Insert suspend fun insert(herramienta: HerramientaEntity)

    //---funciones get---
    @Query("SELECT * FROM herramientas ORDER BY nombre ASC")
    suspend fun getAll(): List<HerramientaEntity>
    @Query("SELECT * FROM herramientas WHERE id = :id")
    suspend fun getById(id: Int): HerramientaEntity?
    //obtener herramienta por nombre
    @Query("SELECT * FROM herramientas WHERE nombre LIKE '%' || :nombre || '%'")
    suspend fun buscarPorNombre(nombre: String): List<HerramientaEntity>
    //obtener herramienta por estado prestada
    @Query("SELECT * FROM herramientas WHERE prestada = :prestada")
    suspend fun buscarPorPrestada(prestada: Boolean): List<HerramientaEntity>
    //obtener por nombre y estado prestada
    @Query("""SELECT * FROM herramientas WHERE nombre LIKE '%' || :nombre || '%' AND prestada = :prestada """)
    suspend fun buscarPorNombreYPrestada(nombre: String, prestada: Boolean): List<HerramientaEntity>

    //---funciones patch---
    @Update suspend fun update(herramienta: HerramientaEntity)
    //Actualizar cantidad actual (limite inferior en 0)
    @Query("""
    UPDATE herramientas SET cantidad_actual = CASE 
        WHEN cantidad_actual + :cantidad < 0 THEN 0 ELSE cantidad_actual + :cantidad 
    END WHERE id = :id """)
    suspend fun actualizarCantidad(id: Int, cantidad: Int)
    @Query("UPDATE herramientas SET prestada = 1 WHERE id = :id")
    suspend fun marcarComoPrestada(id: Int)

    // Marcar herramienta como DEVUELTA (solo si no hay préstamos activos)
    @Query(""" UPDATE herramientas SET prestada = 0 WHERE id = :id """)
    suspend fun marcarComoDevuelta(id: Int)
}