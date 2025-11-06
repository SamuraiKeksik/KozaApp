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
import retrofit2.http.Query
import retrofit2.http.QueryMap

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
    suspend fun getAdvertisements(
        @Query("user_id") userId: String? = null,

        // category_id: uuid.UUID | None
        @Query("category_id") categoryId: String? = null,

        // subcategory_id: uuid.UUID | None
        @Query("subcategory_id") subcategoryId: String? = null,

        // limit: int | None = Field(default=30, ge=1, le=100)
        @Query("limit") limit: Int?,

        // offset: int | None = Field(default=0, ge=0)
        @Query("offset") offset: Int?
    ): Response<List<Advertisement>>
}