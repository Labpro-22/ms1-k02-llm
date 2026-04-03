package com.nimo.nimons360.data.remote

import com.nimo.nimons360.data.local.TokenManager
import com.nimo.nimons360.data.remote.service.AuthApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConfig {
    private const val BASE_URL = "https://mad.labpro.hmif.dev"

    fun provideAuthApiService(tokenManager: TokenManager, onUnauthorized: () -> Unit): AuthApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(ErrorInterceptor(onUnauthorized))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApiService::class.java)
    }
}