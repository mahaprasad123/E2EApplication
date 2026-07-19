package com.example.e2eapp

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Renamed to avoid potential directory conflicts if "Email" was corrupted
val Context.dataStore by preferencesDataStore(name = "user_email_prefs")
val SENDER_KEY = stringPreferencesKey("sender")

suspend fun saveValueToStore(
    context: Context,
    value: String,
) {
    try {
        context.dataStore.edit { prefs ->
            prefs[SENDER_KEY] = value
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun getValueFromStore(
    context: Context,
    key: Preferences.Key<String>,
): Flow<String?> =
    context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { it[key] }

fun provideAPIKey() = "reqres_58bc88cc729a4e55b60044de1fe44f22"
