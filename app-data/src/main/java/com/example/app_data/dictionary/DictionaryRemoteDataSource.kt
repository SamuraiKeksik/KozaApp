package com.example.app_data.dictionary

import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessEntity
import com.example.app_data.network.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class DictionaryRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getArticlesList(
        animalType: AnimalType? = null,
        category: ArticleCategory? = null,
        limit: Int? = null,
        offset: Int? = null
    ): List<ArticleEntity> {
        val response = apiService.getArticles(
            animalType = animalType,
            category = category,
            limit = limit,
            offset = offset,
        )
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw HttpException(response)
        }
    }

    suspend fun getArticleById(
        articleId: Int,
    ): ArticleEntity {
        val response = apiService.getArticleById(
            articleId = articleId.toString(),
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }

    suspend fun getSicknessesList(
        animalType: AnimalType? = null,
        limit: Int? = null,
        offset: Int? = null
    ): List<SicknessEntity> {
        val response = apiService.getSicknesses(
            animalType = animalType,
            limit = limit,
            offset = offset,
        )
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw HttpException(response)
        }
    }

    suspend fun getSicknessById(
        sicknessId: Int,
    ): SicknessEntity {
        val response = apiService.getSicknessById(
            sicknessId = sicknessId.toString(),
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }

}