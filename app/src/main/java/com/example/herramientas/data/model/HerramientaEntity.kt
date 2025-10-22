package com.example.herramientas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "herramientas")
data class HerramientaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val cantidad_actual: Int,
    val prestada: Boolean
)
