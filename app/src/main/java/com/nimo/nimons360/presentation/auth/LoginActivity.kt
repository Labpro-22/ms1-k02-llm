package com.nimo.nimons360.presentation.auth

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nimo.nimons360.R
import kotlinx.coroutines.launch
import com.nimo.nimons360.databinding.ActivityLoginBinding
import kotlin.getValue

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
        observeUiState()
    }

    private fun setUpListeners() {
        binding.btnSignIn.setOnClickListener {
            clearErrors()
            viewModel.onSignInClicked(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
            )
        }

        binding.btnShowPassword.setOnClickListener {
            togglePasswordVisibility()
        }


        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) clearEmailError()
        }
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) clearPasswordError()
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUiState.Idle -> renderIdle()
                        is LoginUiState.Loading -> renderLoading()
                        is LoginUiState.Success -> handleSuccess(state.userId)
                        is LoginUiState.Error -> renderError(state.message)
                        is LoginUiState.ValidationError -> renderValidationErrors(state)
                    }
                }
            }
        }
    }

    private fun renderIdle() {
        binding.progressBar.visibility = View.GONE
        binding.btnSignIn.isEnabled = true
        binding.btnSignIn.text = getString(R.string.login_button_sign_in)
    }

    private fun renderLoading() {
        clearErrors()
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSignIn.isEnabled = false
        binding.btnSignIn.text = getString(R.string.login_loading)
    }

    private fun handleSuccess(userId: String) {
        binding.progressBar.visibility = View.GONE
        binding.btnSignIn.isEnabled = true
        binding.btnSignIn.text = getString(R.string.login_button_sign_in)

        // TODO: Navigate to the main screen.
        // Example:
        //   startActivity(Intent(this, MainActivity::class.java))
        //   finish()
        //
        // If using Jetpack Navigation:
        //   findNavController(R.id.navHostFragment).navigate(R.id.action_login_to_home)
    }

    private fun renderError(errorTag: String) {
        binding.progressBar.visibility = View.GONE
        binding.btnSignIn.isEnabled = true
        binding.btnSignIn.text = getString(R.string.login_button_sign_in)

        val message = resolveErrorMessage(errorTag)
        binding.tvGeneralError.text = message
        binding.tvGeneralError.visibility = View.VISIBLE
    }

    private fun renderValidationErrors(state: LoginUiState.ValidationError) {
        state.emailError?.let { tag ->
            binding.tvEmailError.text = resolveErrorMessage(tag)
            binding.tvEmailError.visibility = View.VISIBLE
            binding.llEmailField.setBackgroundResource(R.drawable.bg_input_field_error)
        }
        state.passwordError?.let { tag ->
            binding.tvPasswordError.text = resolveErrorMessage(tag)
            binding.tvPasswordError.visibility = View.VISIBLE
            binding.llPasswordField.setBackgroundResource(R.drawable.bg_input_field_error)
        }
    }


    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        val cursorPosition = binding.etPassword.selectionEnd

        if (isPasswordVisible) {
            binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.btnShowPassword.setImageResource(R.drawable.ic_visibility)
        } else {
            binding.etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.btnShowPassword.setImageResource(R.drawable.ic_visibility_off)
        }

        binding.etPassword.setSelection(cursorPosition.coerceAtMost(binding.etPassword.text.length))
    }

    private fun clearErrors() {
        clearEmailError()
        clearPasswordError()
        binding.tvGeneralError.visibility = View.GONE
        binding.tvGeneralError.text = ""
    }

    private fun clearEmailError() {
        binding.tvEmailError.visibility = View.GONE
        binding.tvEmailError.text = ""
        binding.llEmailField.setBackgroundResource(R.drawable.bg_input_field)
    }

    private fun clearPasswordError() {
        binding.tvPasswordError.visibility = View.GONE
        binding.tvPasswordError.text = ""
        binding.llPasswordField.setBackgroundResource(R.drawable.bg_input_field)
    }


    private fun resolveErrorMessage(tag: String): String = when (tag) {
        LoginViewModel.ERROR_TAG_EMAIL_EMPTY -> getString(R.string.login_error_email_empty)
        LoginViewModel.ERROR_TAG_EMAIL_INVALID -> getString(R.string.login_error_email_invalid)
        LoginViewModel.ERROR_TAG_PASSWORD_EMPTY -> getString(R.string.login_error_password_empty)
        LoginViewModel.ERROR_TAG_PASSWORD_SHORT -> getString(R.string.login_error_password_too_short)
        LoginViewModel.ERROR_TAG_INVALID_CREDENTIALS -> getString(R.string.login_error_invalid_credentials)
        LoginViewModel.ERROR_TAG_NETWORK -> getString(R.string.login_error_network)
        else -> getString(R.string.login_error_generic)
    }
}