package com.example.fbcv_server

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document(collection = "partidos")
data class Partido(
    @Id val id: String? = null,
    val competicionId: String? = null,
    val categoria: String? = null,
    val fase: String? = null,
    val local: EquipoPartido,
    val visitante: EquipoPartido,
    val puntosLocal: Int? = null,
    val puntosVisitante: Int? = null,
    val fecha: String,
    val hora: String,
    val jornada: String? = null,
    val pabellon: Pabellon? = null,
    val enDirecto: Boolean = false
)

data class EquipoPartido(
    val clubId: String? = null,
    val nombre: String,
    val escudo: String? = null
)

data class Pabellon(
    val nombre: String,
    val direccion: String? = null
)

interface PartidoRepository : MongoRepository<Partido, String> {
    fun findByEnDirectoTrue(): List<Partido>

    fun findByLocalClubIdOrVisitanteClubId(localClubId: String, visitanteClubId: String): List<Partido>
}