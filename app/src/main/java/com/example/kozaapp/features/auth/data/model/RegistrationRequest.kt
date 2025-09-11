package com.example.kozaapp.data.network

data class RegistrationRequest(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val phone: String = "",
    val region: String = "",
)