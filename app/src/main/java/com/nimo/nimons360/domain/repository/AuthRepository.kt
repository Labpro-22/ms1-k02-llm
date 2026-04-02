package com.nimo.nimons360.domain.repository

import com.nimo.nimons360.domain.model.User

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class InvalidCredentials(val serverMessage: String? = null) : AuthResult()
    data class NetworkError(val cause: Throwable? = null) : AuthResult()
    data class UnknownError(val cause: Throwable? = null) : AuthResult()
}

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
//    fun logout()
//    fun getToken(): String?
}