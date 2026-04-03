package com.nimo.nimons360.data.remote

import com.nimo.nimons360.data.local.TokenManager
import com.nimo.nimons360.data.remote.request.LoginRequest
import com.nimo.nimons360.data.remote.service.AuthApiService
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthIntegrationTest {

    private lateinit var apiService: AuthApiService
    private val tokenManager: TokenManager = mockk(relaxed = true)

    @Before
    fun setup() {
        apiService = NetworkConfig.provideAuthApiService(tokenManager) { }
    }

    @Test
    fun `test real login connection to labpro server`() = runBlocking {
        val nim = "isi password yang bener"
        val email = "$nim@std.stei.itb.ac.id"
        val password = nim

        val request = LoginRequest(email, password)

        val response = apiService.login(request)

        println("Response Code: ${response.code()}")
        println("Response Message: ${response.message()}")

        if (response.isSuccessful) {
            val body = response.body()
            println("Login Berhasil! Token: ${body?.data?.token}")
            println("Login Berhasil! User: ${body?.data?.user}")
            assertTrue(response.isSuccessful)
            assertEquals(email, body?.data?.user?.email)
        } else {
            println("Login Gagal! Error Body: ${response.errorBody()?.string()}")
            assertTrue("Koneksi ke server berhasil tapi login gagal", response.code() == 401 || response.code() == 400)
        }
    }
}