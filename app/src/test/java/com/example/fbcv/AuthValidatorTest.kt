package com.example.fbcv

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AuthValidatorTest {

    // ---------- Email ----------

    @Test
    fun email_valido_devuelve_null() {
        val validos = listOf(
            "hola@hotmail.com",
            "juan.perez+liga@correo.es",
            "a@b.co",
            "  hola@hotmail.com  " // los espacios de los extremos se ignoran
        )
        for (email in validos) {
            assertNull("Debería ser válido: $email", AuthValidator.validateEmail(email))
        }
    }

    @Test
    fun email_invalido_devuelve_mensaje_de_error() {
        val invalidos = listOf(
            "", "   ", "hola", "hola@", "@hotmail.com",
            "hola@hotmai",
            "12 em@",
            "hola@@hotmail.com", "hola@hotmail.c", "ho la@hotmail.com",
            "hola@hotmail..com", "hola@.com"
        )
        for (email in invalidos) {
            assertEquals(
                "Debería ser inválido: $email",
                AuthValidator.ERROR_EMAIL,
                AuthValidator.validateEmail(email)
            )
        }
    }

    // ---------- Contrasena ----------

    @Test
    fun password_de_6_o_mas_caracteres_devuelve_null() {
        assertNull(AuthValidator.validatePassword("123456"))
        assertNull(AuthValidator.validatePassword("abcdefgh"))
    }

    @Test
    fun password_corta_devuelve_error() {
        assertEquals(AuthValidator.ERROR_PASSWORD, AuthValidator.validatePassword("12345"))
        assertEquals(AuthValidator.ERROR_PASSWORD, AuthValidator.validatePassword(""))
    }

    // ---------- Nombre ----------

    @Test
    fun nombre_con_texto_devuelve_null() {
        assertNull(AuthValidator.validateName("Héctor"))
    }

    @Test
    fun nombre_vacio_o_solo_espacios_devuelve_error() {
        assertEquals(AuthValidator.ERROR_NAME, AuthValidator.validateName(""))
        assertEquals(AuthValidator.ERROR_NAME, AuthValidator.validateName("   "))
    }

    // ---------- Login ----------

    @Test
    fun login_con_datos_validos_devuelve_null() {
        assertNull(AuthValidator.validateLogin("hola@hotmail.com", "123456"))
    }

    @Test
    fun login_con_email_invalido_devuelve_error_de_email() {
        assertEquals(
            AuthValidator.ERROR_EMAIL,
            AuthValidator.validateLogin("hola@hotmai", "123456")
        )
    }

    @Test
    fun login_con_password_corta_devuelve_error_de_password() {
        assertEquals(
            AuthValidator.ERROR_PASSWORD,
            AuthValidator.validateLogin("hola@hotmail.com", "123")
        )
    }

    @Test
    fun login_con_todo_mal_devuelve_primero_el_error_de_email() {
        assertEquals(
            AuthValidator.ERROR_EMAIL,
            AuthValidator.validateLogin("hola", "1")
        )
    }

    // ---------- Registro ----------

    @Test
    fun registro_con_datos_validos_devuelve_null() {
        assertNull(AuthValidator.validateRegister("Héctor", "hola@hotmail.com", "123456"))
    }

    @Test
    fun registro_sin_nombre_devuelve_error_de_nombre() {
        assertEquals(
            AuthValidator.ERROR_NAME,
            AuthValidator.validateRegister("", "hola@hotmail.com", "123456")
        )
    }

    @Test
    fun registro_con_email_invalido_devuelve_error_de_email() {
        assertEquals(
            AuthValidator.ERROR_EMAIL,
            AuthValidator.validateRegister("Héctor", "12 em@", "123456")
        )
    }

    @Test
    fun registro_con_password_corta_devuelve_error_de_password() {
        assertEquals(
            AuthValidator.ERROR_PASSWORD,
            AuthValidator.validateRegister("Héctor", "hola@hotmail.com", "123")
        )
    }

    @Test
    fun registro_con_todo_mal_devuelve_primero_el_error_de_nombre() {
        assertEquals(
            AuthValidator.ERROR_NAME,
            AuthValidator.validateRegister("", "hola", "1")
        )
    }
}
