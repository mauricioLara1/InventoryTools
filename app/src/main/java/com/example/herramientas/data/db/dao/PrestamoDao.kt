package com.example.herramientas.data.db.dao

import androidx.room.*
import com.example.herramientas.data.model.PrestamoEntity

@Dao
interface PrestamoDao {

    @Insert
    suspend fun insert(prestamo: PrestamoEntity)

    @Query("SELECT * FROM prestamos ORDER BY fecha DESC")
    suspend fun getAll(): List<PrestamoEntity>

    @Query("SELECT * FROM prestamos WHERE id = :id")
    suspend fun getById(id: Int): PrestamoEntity?
}
