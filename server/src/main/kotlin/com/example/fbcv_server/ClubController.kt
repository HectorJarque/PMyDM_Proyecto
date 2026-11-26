package com.example.fbcv_server

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/clubes")
class ClubController(private val repository: ClubRepository) {

    @GetMapping
    fun buscar(@RequestParam(required = false) nombre: String?): List<Club> =
        if (nombre.isNullOrBlank()) repository.findAll()
        else repository.findByNombreContainingIgnoreCase(nombre)

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: String): ResponseEntity<Club> =
        repository.findById(id).orElse(null)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}