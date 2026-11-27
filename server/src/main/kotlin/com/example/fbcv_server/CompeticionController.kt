package com.example.fbcv_server

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/competiciones")
class CompeticionController(private val repository: CompeticionRepository) {

    @GetMapping
    fun listar(
        @RequestParam(required = false) tipo: String?,
        @RequestParam(required = false) categoria: String?,
        @RequestParam(required = false) fase: String?,
        @RequestParam(required = false) grupo: String?
    ): List<CompeticionResumen> =
        repository.findAll()
            .filter { tipo.isNullOrBlank() || it.tipo == tipo }
            .filter { categoria.isNullOrBlank() || it.categoria == categoria }
            .filter { fase.isNullOrBlank() || it.fase == fase }
            .filter { grupo.isNullOrBlank() || it.grupo == grupo }
            .map { CompeticionResumen(it.id.orEmpty(), it.tipo, it.nombre, it.categoria, it.fase, it.grupo) }

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: String): ResponseEntity<Competicion> =
        repository.findById(id).orElse(null)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/{id}/clasificacion")
    fun clasificacion(@PathVariable id: String): ResponseEntity<List<FilaClasificacion>> {
        val competicion = repository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        val ordenada = competicion.clasificacion.sortedWith(
            compareByDescending<FilaClasificacion> { it.puntos }
                .thenByDescending { it.puntosFavor - it.puntosContra }
        )
        return ResponseEntity.ok(ordenada)
    }
}