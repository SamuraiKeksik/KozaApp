package com.example.kozaapp.features.auth.data.model

data class User(
    val email: String,
    val username: String,

    val phone: String,
    val country: String,
    val region: String ,
    val disabled: Boolean,
)