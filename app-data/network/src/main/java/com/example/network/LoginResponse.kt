package com.example.kozaapp.data.network

data class LoginResponse (
    val access_token: String,
    val refresh_token: String,
    val token_type: String,
)