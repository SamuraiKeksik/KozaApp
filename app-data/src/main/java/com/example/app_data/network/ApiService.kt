package com.example.app_data.network

import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import com.example.app_data.dictionary.ArticleCategory
import com.example.app_data.dictionary.ArticleEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/dictionary/articles")
    suspend fun getArticles(
        @Query("animal_type") animalType: AnimalType?,
        @Query("category") category: ArticleCategory?,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): Response<List<ArticleEntity>>

    @GET("/dictionary/articles/{article_id}")
    suspend fun getArticleById(
        @Path("article_id") articleId: String
    ): Response<ArticleEntity>

    @GET("/dictionary/sickness_types")
    suspend fun getSicknessTypes(
        @Query("animal_type") animalType: AnimalType?,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): Response<List<SicknessType>>

    @GET("/dictionary/sickness_types/{sickness_type_id}")
    suspend fun getSicknessTypeById(
        @Path("sickness_type_id") sicknessTypeId: String
    ): Response<SicknessType>
}
//    @GET("/users/me")
//    suspend fun getCurrentUser(): Response<User>
//
//    @GET("/animals/goats")
//    suspend fun getGoatsList(): Response<List<GoatResponse>>
//
//    @POST("/animals/goats")
//    suspend fun createGoat(@Body goatRequest: GoatRequest): Response<GoatResponse>
//
//    @PUT("/animals/goats/{goat_id}")
//    suspend fun updateGoat(
//        @Path("goat_id") goatId: String,
//        @Body goatRequest: GoatRequest,
//        ): Response<GoatResponse>
//
//    @DELETE("/animals/goats/{goat_id}")
//    suspend fun deleteGoat(
//        @Path("goat_id") goatId: String,
//        ): Response<GoatDeletionResponse>
//
//    @GET("/advertisements")
//    suspend fun getAdvertisements(
//        @Query("user_id") userId: String? = null,
//
//        // category_id: uuid.UUID | None
//        @Query("category_id") categoryId: String? = null,
//
//        // subcategory_id: uuid.UUID | None
//        @Query("subcategory_id") subcategoryId: String? = null,
//
//        // limit: int | None = Field(default=30, ge=1, le=100)
//        @Query("limit") limit: Int?,
//
//        // offset: int | None = Field(default=0, ge=0)
//        @Query("offset") offset: Int?
//    ): Response<List<Advertisement>>
//}