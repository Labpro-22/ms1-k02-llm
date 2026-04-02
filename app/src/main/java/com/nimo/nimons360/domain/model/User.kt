package com.nimo.nimons360.domain.model

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val token: String
)