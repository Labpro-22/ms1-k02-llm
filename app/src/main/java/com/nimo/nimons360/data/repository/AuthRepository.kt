package com.nimo.nimons360.data.repository

data class AuthUser(
    val userId: String,
    val email: String,
    val displayName: String,
    val accessToken: String,
)


sealed class AuthResult {
    data class Success(val user: AuthUser) : AuthResult()
    data class InvalidCredentials(val serverMessage: String? = null) : AuthResult()
    data class NetworkError(val cause: Throwable? = null) : AuthResult()
    data class UnknownError(val cause: Throwable? = null) : AuthResult()
}

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
}


class DummyAuthRepository : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        kotlinx.coroutines.delay(1_500)

        return when {
            email == "demo@nimons360.com" && password == "password123" -> {
                AuthResult.Success(
                    AuthUser(
                        userId = "usr_demo_001",
                        email = email,
                        displayName = "Demo User",
                        accessToken = "dummy_access_token_xyz",
                    )
                )
            }
            email.contains("error") -> {
                AuthResult.NetworkError(cause = Exception("Simulated network failure"))
            }
            else -> {
                AuthResult.InvalidCredentials(serverMessage = "Email or password is incorrect.")
            }
        }
    }
}