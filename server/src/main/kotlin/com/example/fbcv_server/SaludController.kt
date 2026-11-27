package com.example.fbcv_server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SaludController(private val clubRepository: ClubRepository) {

    @GetMapping("/api/salud")
    fun salud(): Map<String, String> {
        val baseDeDatos = try {
            "ok (${clubRepository.count()} clubes)"
        } catch (e: Exception) {
            "error: ${e.javaClass.simpleName}"
        }
        return mapOf("servidor" to "ok", "baseDeDatos" to baseDeDatos)
    }
}