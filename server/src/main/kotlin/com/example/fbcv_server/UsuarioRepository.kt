package com.example.fbcv_server

import org.springframework.data.mongodb.repository.MongoRepository

interface UsuarioRepository : MongoRepository<Usuario, String> {
    fun findByEmail(email: String): Usuario?
    fun existsByEmail(email: String): Boolean
}