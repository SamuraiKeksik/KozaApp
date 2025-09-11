package com.example.kozaapp.data.network

import com.example.kozaapp.features.auth.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/users/me")
    fun registration(): Response<User>
}