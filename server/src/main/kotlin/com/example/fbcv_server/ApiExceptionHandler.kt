package com.example.fbcv_server

import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun datosNoValidos(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val mensaje = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Datos no válidos"
        return ResponseEntity.badRequest().body(ErrorResponse(mensaje))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun jsonNoValido(): ResponseEntity<ErrorResponse> =
        ResponseEntity.badRequest().body(ErrorResponse("El cuerpo de la petición no es válido"))
}