package com.example.fbcv_server

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val repository: UsuarioRepository) {

    private val encoder = BCryptPasswordEncoder()

    @PostMapping("/registro")
    fun registro(@Valid @RequestBody request: RegistroRequest): ResponseEntity<Any> {
        val email = request.email.trim().lowercase()

        if (repository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse("Ya existe una cuenta con ese email"))
        }

        val usuario = repository.save(
            Usuario(
                nombre = request.nombre.trim(),
                email = email,
                passwordHash = encoder.encode(request.password)!!
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AuthResponse(usuario.id!!, usuario.nombre, usuario.email))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<Any> {
        val usuario = repository.findByEmail(request.email.trim().lowercase())

        if (usuario == null || !encoder.matches(request.password, usuario.passwordHash)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse("Email o contraseña incorrectos"))
        }
        return ResponseEntity.ok(AuthResponse(usuario.id!!, usuario.nombre, usuario.email))
    }
}