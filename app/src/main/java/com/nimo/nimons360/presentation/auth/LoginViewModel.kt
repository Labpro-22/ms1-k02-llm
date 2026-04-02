package com.nimo.nimons360.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimo.nimons360.domain.repository.AuthRepository
import com.nimo.nimons360.domain.repository.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider

class LoginViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onSignInClicked(email: String, password: String) {
        val validationError = validate(email, password)
        if (validationError != null) {
            _uiState.value = validationError
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = repository.login(email.trim(), password)

            _uiState.value = when (result) {
                is AuthResult.Success -> {
                    LoginUiState.Success(userId = result.user.id)
                }
                is AuthResult.InvalidCredentials -> {
                    LoginUiState.Error(message = ERROR_TAG_INVALID_CREDENTIALS)
                }
                is AuthResult.NetworkError -> {
                    LoginUiState.Error(message = ERROR_TAG_NETWORK)
                }
                is AuthResult.UnknownError -> {
                    LoginUiState.Error(message = ERROR_TAG_GENERIC)
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

    private fun validate(email: String, password: String): LoginUiState.ValidationError? {
        val emailError = when {
            email.isBlank() -> ERROR_TAG_EMAIL_EMPTY
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> ERROR_TAG_EMAIL_INVALID
            else -> null
        }

        val passwordError = when {
            password.isEmpty() -> ERROR_TAG_PASSWORD_EMPTY
            password.length < MIN_PASSWORD_LENGTH -> ERROR_TAG_PASSWORD_SHORT
            else -> null
        }

        return if (emailError != null || passwordError != null) {
            LoginUiState.ValidationError(emailError = emailError, passwordError = passwordError)
        } else {
            null
        }
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6

        const val ERROR_TAG_EMAIL_EMPTY = "error_email_empty"
        const val ERROR_TAG_EMAIL_INVALID = "error_email_invalid"
        const val ERROR_TAG_PASSWORD_EMPTY = "error_password_empty"
        const val ERROR_TAG_PASSWORD_SHORT = "error_password_short"

        const val ERROR_TAG_INVALID_CREDENTIALS = "error_invalid_credentials"
        const val ERROR_TAG_NETWORK = "error_network"
        const val ERROR_TAG_GENERIC = "error_generic"
    }
}

class LoginViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}