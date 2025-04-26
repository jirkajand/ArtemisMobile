package cz.esnhk.artemisMobile.viewmodels


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.api.ApiClient
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.repository.TOKEN_KEY
import cz.esnhk.artemisMobile.repository.dataStore
import cz.esnhk.artemisMobile.services.LoginRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(private val context: Context) : ViewModel() {


    var isLoggedIn by mutableStateOf(false)
        private set

    val loginState = mutableStateOf<ApiResult<Unit>>(ApiResult.Success(Unit))

    private val dataStore = context.dataStore // see extension below

    init {
        viewModelScope.launch {
            val token = dataStore.data.first()[TOKEN_KEY]
            isLoggedIn = !token.isNullOrBlank()
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        // Use an all caps tag as this is the convention
        val TAG = "AUTH_VIEW_MODEL"

        // This should definitely show up
        Log.e(TAG, "===============================================")
        Log.e(TAG, "LOGIN ATTEMPT STARTED")
        Log.e(TAG, "===============================================")

        viewModelScope.launch {
            loginState.value = ApiResult.Loading
            try {
                Log.e(TAG, "About to call API")
                val response = ApiClient.authService.login(LoginRequest(username, password))
                Log.e(TAG, "API call to the: ${ApiClient.BASE_URL}")
                Log.e(TAG, "API call completed with status: ${response.code()}")

                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    Log.e(TAG, "Login successful")

                    // Log before saving to datastore
                    Log.e(TAG, "Saving token to datastore")
                    dataStore.edit { it[TOKEN_KEY] = token }

                    // Log after saving token
                    Log.e(TAG, "Setting isLoggedIn to true")
                    isLoggedIn = true

                    Log.e(TAG, "Calling success callback")
                    loginState.value = ApiResult.Success(Unit)
                    onSuccess()
                } else { //TODO: rozlišit chyby
                    Log.e(TAG, "Login failed with code: ${response.code()}")
                    loginState.value = ApiResult.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in login: ${e.javaClass.simpleName}")
                Log.e(TAG, "Exception message: ${e.message}")
                e.printStackTrace() // This should print to Logcat regardless of tag filtering
                loginState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun logout() {
        dataStore.edit { it.remove(TOKEN_KEY) }
        isLoggedIn = false
    }
}
