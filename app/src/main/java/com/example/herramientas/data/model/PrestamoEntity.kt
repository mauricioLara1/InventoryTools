package com.example.herramientas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prestamos",
    foreignKeys = [
        ForeignKey(
            entity = HerramientaEntity::class,
            parentColumns = ["id"],
            childColumns = ["herramienta_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PrestamoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val herramienta_id: Int,
    val persona: String,
    val fecha: String,
    val prestamo_activo: Boolean,
    val comentario: String? = null
)
