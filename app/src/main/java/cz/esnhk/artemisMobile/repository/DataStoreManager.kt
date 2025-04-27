package cz.esnhk.artemisMobile.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Extension property to get DataStore instance from any context
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

// Token key constant
val TOKEN_KEY = stringPreferencesKey("auth_token")


class DataStoreManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    // Flow that emits whether the user is logged in (token exists)
    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]?.isNotBlank() == true
    }

    // Save token to DataStore
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Remove token from DataStore
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    // Optional: Retrieve token (if needed for API calls etc.)
    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            if (loggedIn) {
                preferences[TOKEN_KEY] = getToken().toString()
            } else {
                preferences.remove(TOKEN_KEY)
            }
        }
    }

    suspend fun getToken(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    suspend fun getUserId(): Int {
        try {
            val preferences = context.dataStore.data.first()
            // Assuming your token contains a user ID (you need to extract it from the token)
            // Decode the token to get user ID, or directly save the user ID in DataStore when logged in.
            val token = preferences[TOKEN_KEY]
            return if (token != null) {
                // Extract user ID from the token here (if needed)
                getUserIdFromToken(token)
            } else {
                return 0
            }
        } catch (e: Exception) {
            Log.e("DataStoreManager", "Error getting user ID: ${e.message}")
            return 0
        }
    }

    fun getUserIdFromToken(token: String?): Int {
        if (token == null) throw Exception("Token is null")

        try {
            // Decode the JWT
            val jwt = JWT(token)

            // Extract user ID from the payload
            // Assuming the user ID is stored in the "user_id" claim
            val userId = jwt.getClaim("user_id").asInt()

            return userId?.toInt() ?: 0
        } catch (e: Exception) {
            throw Exception("Error decoding token: ${e.message}")
        }
    }
}