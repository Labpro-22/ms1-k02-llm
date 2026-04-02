package com.nimo.nimons360.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val data: LoginData
)

data class LoginData(
    @SerializedName("token")
    val token: String,

    @SerializedName("expiresAt")
    val expiresAt: String,

    @SerializedName("user")
    val user: UserResponse
)