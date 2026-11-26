package com.example.fbcv_server

import org.springframework.data.mongodb.repository.MongoRepository

interface ClubRepository : MongoRepository<Club, String> {
    fun findByNombreContainingIgnoreCase(nombre: String): List<Club>
}