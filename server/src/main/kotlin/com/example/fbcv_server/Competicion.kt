package com.example.fbcv_server

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document(collection = "competiciones")
data class Competicion(
    @Id val id: String? = null,
    val tipo: String,
    val nombre: String,
    val categoria: String,
    val fase: String,
    val grupo: String,
    val clasificacion: List<FilaClasificacion> = emptyList()
)

data class FilaClasificacion(
    val equipo: String,
    val clubId: String? = null,
    val partidos: Int = 0,
    val victorias: Int = 0,
    val derrotas: Int = 0,
    val puntosFavor: Int = 0,
    val puntosContra: Int = 0,
    val puntos: Int = 0
)

data class CompeticionResumen(
    val id: String,
    val tipo: String,
    val nombre: String,
    val categoria: String,
    val fase: String,
    val grupo: String
)

interface CompeticionRepository : MongoRepository<Competicion, String>