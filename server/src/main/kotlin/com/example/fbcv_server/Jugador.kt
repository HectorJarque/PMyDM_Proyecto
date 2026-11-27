package com.example.fbcv_server

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document(collection = "jugadores")
data class Jugador(
    @Id val id: String? = null,
    val nombre: String,
    val clubId: String,
    val equipoId: String? = null,
    val estadisticas: EstadisticasJugador = EstadisticasJugador()
)

data class EstadisticasJugador(
    val partidosJugados: Int = 0,
    val puntosTotales: Int = 0,
    val minutosTotales: Int = 0,
    val minutosPorPartido: Int = 0
)

interface JugadorRepository : MongoRepository<Jugador, String> {
    fun findByClubId(clubId: String): List<Jugador>
}