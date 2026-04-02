package com.nimo.nimons360.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nim")
    val nim: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("fullName")
    val fullName: String
)