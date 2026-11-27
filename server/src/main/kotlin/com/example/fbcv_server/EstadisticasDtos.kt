package com.example.fbcv_server

data class TiroResumen(val aciertos: Int, val intentos: Int, val porcentaje: Int)

data class EstadisticasClubResponse(
    val equipo: String,
    val partidos: Int,
    val victorias: Int,
    val derrotas: Int,
    val puntosFavor: Int,
    val puntosFavorPorPartido: Int,
    val puntosContra: Int,
    val puntosContraPorPartido: Int,
    val tirosLibres: TiroResumen,
    val tiros2: TiroResumen,
    val tiros3: TiroResumen,
    val jugadores: List<Jugador>
)