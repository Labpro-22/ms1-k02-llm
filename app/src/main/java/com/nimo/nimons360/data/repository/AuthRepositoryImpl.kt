package com.nimo.nimons360.data.repository

import com.nimo.nimons360.data.local.TokenManager
import com.nimo.nimons360.data.remote.request.LoginRequest
import com.nimo.nimons360.data.remote.service.AuthApiService
import com.nimo.nimons360.domain.model.User
import com.nimo.nimons360.domain.repository.AuthRepository
import com.nimo.nimons360.domain.repository.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(LoginRequest(email, password))

                if (response.isSuccessful) {
                    val body = response.body()?.data
                    if (body != null) {
                        tokenManager.saveToken(body.token)

                        val domainUser = User(
                            id = body.user.id.toString(),
                            email = body.user.email,
                            fullName = body.user.fullName,
                            token = body.token
                        )

                        AuthResult.Success(domainUser)
                    } else {
                        AuthResult.UnknownError(Exception("Data tidak ditemukan"))
                    }
                } else {
                    val msg = if (response.code() == 401) "Email/Password salah" else "Gagal: ${response.code()}"
                    AuthResult.InvalidCredentials(msg)
                }
            } catch (e: IOException) {
                AuthResult.NetworkError(e)
            } catch (e: Exception) {
                AuthResult.UnknownError(e)
            }
        }
    }
}