package com.nimo.nimons360.data.remote.service

import com.nimo.nimons360.data.remote.request.LoginRequest
import com.nimo.nimons360.data.remote.response.LoginResponse
import com.nimo.nimons360.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/me")
    suspend fun getProfile(): Response<UserResponse>
}