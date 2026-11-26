package com.example.fbcv

object AuthValidator {
    const val MIN_PASSWORD_LENGTH = 6
    const val ERROR_NAME = "Escribe tu nombre"
    const val ERROR_EMAIL = "El email no es válido"
    const val ERROR_PASSWORD = "La contraseña debe tener al menos $MIN_PASSWORD_LENGTH caracteres"
    private val EMAIL_REGEX =
        Regex("""[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*\.[A-Za-z]{2,}""")

    fun validateName(name: String): String? =
        if (name.isBlank()) ERROR_NAME else null

    fun validateEmail(email: String): String? =
        if (EMAIL_REGEX.matches(email.trim())) null else ERROR_EMAIL

    fun validatePassword(password: String): String? =
        if (password.length < MIN_PASSWORD_LENGTH) ERROR_PASSWORD else null
    fun validateLogin(email: String, password: String): String? =
        validateEmail(email) ?: validatePassword(password)

    fun validateRegister(name: String, email: String, password: String): String? =
        validateName(name) ?: validateEmail(email) ?: validatePassword(password)
}
