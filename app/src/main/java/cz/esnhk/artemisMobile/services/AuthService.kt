package cz.esnhk.artemisMobile.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("token/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)