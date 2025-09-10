package com.example.kozaapp.data.network

import com.example.kozaapp.auth.data.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/users/me")
    fun registration(): Response<User>
}