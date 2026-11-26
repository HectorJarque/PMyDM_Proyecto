package com.example.fbcv

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {

    private val loggedKey = booleanPreferencesKey("logged_in")
    private val nameKey = stringPreferencesKey("user_name")
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[loggedKey] ?: false }
    val userName: Flow<String> = context.dataStore.data.map { it[nameKey] ?: "" }

    suspend fun saveSession(name: String) {
        context.dataStore.edit {
            it[loggedKey] = true
            it[nameKey] = name
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
