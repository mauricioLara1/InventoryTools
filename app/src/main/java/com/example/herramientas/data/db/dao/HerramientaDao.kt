package com.example.herramientas.data.db.dao


import com.example.herramientas.data.model.HerramientaEntity
import androidx.room.*

@Dao
interface HerramientaDao {

    //Insertar una herramienta
    @Insert suspend fun insert(herramienta: HerramientaEntity)

    //Obtener todas
    @Query("SELECT * FROM herramientas ORDER BY nombre ASC")
    suspend fun getAll(): List<HerramientaEntity>

    //Obtener por ID
    @Query("SELECT * FROM herramientas WHERE id = :id")
    suspend fun getById(id: Int): HerramientaEntity?

    //Actualizar herramienta completa
    @Update
    suspend fun update(herramienta: HerramientaEntity)

    //Actualizar cantidad actual (limite inferior en 0)
    @Query("""
        UPDATE herramientas 
        SET cantidad_actual = CASE 
            WHEN cantidad_actual - :cantidad < 0 THEN 0 
            ELSE cantidad_actual - :cantidad 
        END
        WHERE id = :id
    """)
    suspend fun reducirCantidad(id: Int, cantidad: Int)
    // Marcar herramienta como PRESTADA (sin condición)
    @Query("UPDATE herramientas SET prestada = 1 WHERE id = :id")
    suspend fun marcarComoPrestada(id: Int)

    // Marcar herramienta como DEVUELTA (solo si no hay préstamos activos)
    @Query("""
        UPDATE herramientas 
        SET prestada = 0 
        WHERE id = :id
    """)
    suspend fun marcarComoDevuelta(id: Int)
}