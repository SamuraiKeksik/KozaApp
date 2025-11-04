package com.example.kozaapp.data.network

import com.example.kozaapp.features.advertisements.data.model.Advertisement
import com.example.kozaapp.features.advertisements.data.schemas.GetAdvertisementsRequest
import com.example.kozaapp.features.animals.goats.data.schemas.GoatDeletionResponse
import com.example.kozaapp.features.animals.goats.data.schemas.GoatRequest
import com.example.kozaapp.features.animals.goats.data.schemas.GoatResponse
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.auth.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/users/me")
    suspend fun getCurrentUser(): Response<User>

    @GET("/animals/goats")
    suspend fun getGoatsList(): Response<List<GoatResponse>>

    @POST("/animals/goats")
    suspend fun createGoat(@Body goatRequest: GoatRequest): Response<GoatResponse>

    @PUT("/animals/goats/{goat_id}")
    suspend fun updateGoat(
        @Path("goat_id") goatId: String,
        @Body goatRequest: GoatRequest,
        ): Response<GoatResponse>

    @DELETE("/animals/goats/{goat_id}")
    suspend fun deleteGoat(
        @Path("goat_id") goatId: String,
        ): Response<GoatDeletionResponse>

    @GET("/advertisements")
    suspend fun getAdvertisements(request: GetAdvertisementsRequest): Response<List<Advertisement>>
}