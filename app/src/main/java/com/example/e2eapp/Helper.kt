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

val Context.dataStore by preferencesDataStore(name = "Email")
val SENDER_KEY = stringPreferencesKey("sender")

suspend fun saveValueToStore(
    context: Context,
    value: String,
) {
    context.dataStore.edit { prefs ->
        prefs[SENDER_KEY] = value
    }
}

// Fixed: Removed 'suspend' and corrected return type to Flow
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
