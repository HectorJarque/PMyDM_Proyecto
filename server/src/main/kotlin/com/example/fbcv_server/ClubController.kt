package com.example.fbcv_server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.math.roundToInt

@RestController
@RequestMapping("/api/clubes")
class ClubController(
    private val clubRepository: ClubRepository,
    private val partidoRepository: PartidoRepository,
    private val jugadorRepository: JugadorRepository
) {

    @GetMapping
    fun buscar(@RequestParam(required = false) nombre: String?): List<Club> =
        if (nombre.isNullOrBlank()) clubRepository.findAll()
        else clubRepository.findByNombreContainingIgnoreCase(nombre)

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: String): ResponseEntity<Club> =
        clubRepository.findById(id).orElse(null)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/{id}/estadisticas")
    fun estadisticas(
        @PathVariable id: String,
        @RequestParam(required = false) equipoId: String?
    ): ResponseEntity<Any> {
        val club = clubRepository.findById(id).orElse(null)
            ?: return noEncontrado("Club no encontrado")
        val equipo = club.equipos.firstOrNull { equipoId == null || it.equipoId == equipoId }
            ?: return noEncontrado("El club no tiene ese equipo")
        val stats = equipo.estadisticas
            ?: return noEncontrado("El equipo no tiene estadísticas")

        val jugadores = jugadorRepository.findByClubId(id)
            .filter { it.equipoId == null || it.equipoId == equipo.equipoId }
            .sortedBy { it.nombre }

        return ResponseEntity.ok(
            EstadisticasClubResponse(
                equipo = equipo.nombre,
                partidos = stats.partidos,
                victorias = stats.victorias,
                derrotas = stats.derrotas,
                puntosFavor = stats.puntosFavor,
                puntosFavorPorPartido = media(stats.puntosFavor, stats.partidos),
                puntosContra = stats.puntosContra,
                puntosContraPorPartido = media(stats.puntosContra, stats.partidos),
                tirosLibres = resumen(stats.tirosLibres),
                tiros2 = resumen(stats.tiros2),
                tiros3 = resumen(stats.tiros3),
                jugadores = jugadores
            )
        )
    }

    @GetMapping("/{id}/partidos")
    fun calendario(
        @PathVariable id: String,
        @RequestParam(required = false) mes: String?
    ): List<Partido> {
        val partidos = partidoRepository.findByLocalClubIdOrVisitanteClubId(id, id)
        val delMes = if (mes.isNullOrBlank()) partidos else partidos.filter { it.fecha.startsWith(mes) }
        return delMes.sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    private fun media(total: Int, partidos: Int): Int =
        if (partidos == 0) 0 else (total.toDouble() / partidos).roundToInt()

    private fun resumen(tiro: Tiro): TiroResumen =
        TiroResumen(
            aciertos = tiro.aciertos,
            intentos = tiro.intentos,
            porcentaje = if (tiro.intentos == 0) 0 else (tiro.aciertos * 100.0 / tiro.intentos).roundToInt()
        )

    private fun noEncontrado(mensaje: String): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(mensaje))
}