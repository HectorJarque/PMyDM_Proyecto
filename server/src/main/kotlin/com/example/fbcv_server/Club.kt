package com.example.fbcv_server

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "clubes")
data class Club(
    @Id val id: String? = null,
    val nombre: String,
    val escudo: String? = null,
    val contacto: Contacto? = null,
    val equipacion: Equipacion? = null
)

data class Contacto(
    val persona: String? = null,
    val telefono: String? = null,
    val email: String? = null
)

data class Equipacion(
    val camiseta: String? = null,
    val pantalones: String? = null
)