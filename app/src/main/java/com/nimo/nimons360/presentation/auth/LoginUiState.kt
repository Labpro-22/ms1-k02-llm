package com.nimo.nimons360.presentation.auth

sealed class LoginUiState {

    object Idle : LoginUiState()

    object Loading : LoginUiState()

    data class Success(val userId: String) : LoginUiState()

    data class Error(val message: String) : LoginUiState()

    data class ValidationError(
        val emailError: String? = null,
        val passwordError: String? = null,
    ) : LoginUiState()
}