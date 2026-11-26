package com.example.fbcv_server

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

private const val EMAIL_REGEX = "[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}"

data class RegistroRequest(
    @field:NotBlank(message = "Escribe tu nombre")
    val nombre: String,

    @field:Pattern(regexp = EMAIL_REGEX, message = "El email no es válido")
    val email: String,

    @field:Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    val password: String
)

data class LoginRequest(
    @field:Pattern(regexp = EMAIL_REGEX, message = "El email no es válido")
    val email: String,

    @field:NotBlank(message = "Escribe la contraseña")
    val password: String
)

data class AuthResponse(val id: String, val nombre: String, val email: String)
data class ErrorResponse(val mensaje: String)