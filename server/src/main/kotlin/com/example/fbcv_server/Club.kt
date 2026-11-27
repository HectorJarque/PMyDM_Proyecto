package com.example.fbcv_server

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "clubes")
data class Club(
    @Id val id: String? = null,
    val nombre: String,
    val escudo: String? = null,
    val contacto: Contacto? = null,
    val equipacion: Equipacion? = null,
    val equipos: List<Equipo> = emptyList()
)

data class Contacto(
    val persona: String? = null,
    val telefono: String? = null,
    val email: String? = null
)

data class Equipacion(
    val camiseta: String? = null,
    val pantalones: String? = null
)

data class Equipo(
    val id: String,
    val nombre: String,
    val categoria: String? = null,
    val competicionId: String? = null,
    val grupo: String? = null,
    val estadisticas: EstadisticasEquipo? = null
)

data class EstadisticasEquipo(
    val partidos: Int = 0,
    val victorias: Int = 0,
    val derrotas: Int = 0,
    val puntosFavor: Int = 0,
    val puntosContra: Int = 0,
    val tirosLibres: Tiro = Tiro(),
    val tiros2: Tiro = Tiro(),
    val tiros3: Tiro = Tiro()
)

data class Tiro(val aciertos: Int = 0, val intentos: Int = 0)