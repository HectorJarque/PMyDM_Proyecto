package com.example.fbcv_server

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/partidos")
class PartidoController(private val repository: PartidoRepository) {
    @GetMapping
    fun listar(
        @RequestParam(required = false) enDirecto: Boolean?,
        @RequestParam(required = false) q: String?
    ): List<Partido> {
        val base = if (enDirecto == true) repository.findByEnDirectoTrue() else repository.findAll()
        val texto = q?.trim().orEmpty()
        val filtrados = if (texto.isEmpty()) base else base.filter { coincide(it, texto) }
        return filtrados.sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: String): ResponseEntity<Partido> =
        repository.findById(id).orElse(null)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    private fun coincide(partido: Partido, texto: String): Boolean =
        listOf(partido.local.nombre, partido.visitante.nombre, partido.categoria.orEmpty())
            .any { it.contains(texto, ignoreCase = true) }
}