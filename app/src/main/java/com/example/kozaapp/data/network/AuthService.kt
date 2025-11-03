package com.example.kozaapp.data.network

import com.example.kozaapp.features.auth.data.model.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("/users/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Response<LoginResponse>

    @POST("/users/registration")
    suspend fun registration(
        @Body request: RegistrationRequest,
    ) : Response<RegistrationResponse>

}