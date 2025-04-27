package cz.esnhk.artemisMobile.viewmodels


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.api.ApiClient
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.repository.DataStoreManager
import cz.esnhk.artemisMobile.services.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(
    private val context: Context,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    var isLoggedInFlow = dataStoreManager.isLoggedInFlow

    private val _loginState = MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle)
    val loginState: StateFlow<ApiResult<Unit>> = _loginState

    init {
        viewModelScope.launch {
            val token = dataStoreManager.tokenFlow.first()
        }
    }

    fun login(username: String, password: String) {
        // Use an all caps tag as this is the convention
        val TAG = "AUTH_VIEW_MODEL"

        // This should definitely show up
        Log.e(TAG, "===============================================")
        Log.e(TAG, "LOGIN ATTEMPT STARTED")
        Log.e(TAG, "===============================================")

        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            try {
                Log.e(TAG, "About to call API")
                val response = ApiClient.authService.login(LoginRequest(username, password))
                Log.e(TAG, "API call to the: ${ApiClient.BASE_URL}")
                Log.e(TAG, "API call completed with status: ${response.code()}")

                if (response.isSuccessful) {
                    val token = response.body()?.access ?: throw Exception("Token is null")
                    Log.e(TAG, "Login successful")

                    // Log before saving to datastore
                    Log.e(TAG, "Saving token to datastore")
                    Log.e(TAG, "Token: $token")
                    dataStoreManager.saveToken(token)
                    Log.e(TAG, "Stored token: ${dataStoreManager.getToken()}")

                    Log.e(TAG, "Calling success callback")
                    _loginState.value = ApiResult.Success(Unit)
                } else { //TODO: rozlišit chyby
                    Log.e(TAG, "Login failed with code: ${response.code()}")
                    _loginState.value = ApiResult.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in login: ${e.javaClass.simpleName}")
                Log.e(TAG, "Exception message: ${e.message}")
                e.printStackTrace() // This should print to Logcat regardless of tag filtering
                _loginState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun logout() {
        dataStoreManager.clearToken();
    }
}
