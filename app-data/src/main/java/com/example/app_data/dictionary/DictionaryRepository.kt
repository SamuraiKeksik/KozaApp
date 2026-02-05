package com.example.app_data.dictionary

import android.util.Log
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject


interface DictionaryRepository {
    fun getArticlesByCategoryAndAnimalType(
        animalType: AnimalType,
        articleCategory: ArticleCategory
    ): Flow<List<ArticleEntity>>

    fun getSicknessesTypesByAnimalType(animalType: AnimalType): Flow<List<SicknessType>>

    suspend fun synchronizeArticlesWithServer()
    suspend fun synchronizeSicknessTypesWithServer()

}

class DefaultDictionaryRepository @Inject constructor(
    private val dictionaryLocalDataSource: DictionaryLocalDataSource,
    private val dictionaryRemoteDataSource: DictionaryRemoteDataSource
) : DictionaryRepository {
    override fun getArticlesByCategoryAndAnimalType(
        animalType: AnimalType,
        articleCategory: ArticleCategory
    ): Flow<List<ArticleEntity>> {
        if (animalType == AnimalType.ALL && articleCategory != ArticleCategory.ALL) {
            return dictionaryLocalDataSource.getArticlesByCategoryFlow(articleCategory = articleCategory)
        }
        if (animalType == AnimalType.ALL && articleCategory == ArticleCategory.ALL) {
            return dictionaryLocalDataSource.getAllArticlesFlow()
        }
        return dictionaryLocalDataSource.getArticlesByCategoryAndAnimalTypeFlow(
            animalType = animalType,
            articleCategory = articleCategory
        )
    }

    override fun getSicknessesTypesByAnimalType(animalType: AnimalType): Flow<List<SicknessType>> {
        if (animalType == AnimalType.ALL) {
            return dictionaryLocalDataSource.getAllSicknessesTypesFlow()
        }
        return dictionaryLocalDataSource.getSicknessesTypesByAnimalTypeFlow(animalType = animalType)
    }

    //Retrofit
    override suspend fun synchronizeArticlesWithServer() {
        try {
            while (checkNewArticle()) {
                val lastArticleId = dictionaryLocalDataSource.getLastArticleId()
                val articlesList = dictionaryRemoteDataSource.getArticlesList(
                    offset = lastArticleId + 1,
                    limit = 50
                )
                articlesList.forEach {
                    dictionaryLocalDataSource.insertArticle(it)
                }
            }
        } catch (e: HttpException) {
            Log.w("ApiService", e.message.toString())
        }
        catch (e: Exception) {
            Log.e("ApiService","ApiService unexpected Error")
            Log.e("ApiService", e.message.toString())
        }
    }

    private suspend fun checkNewArticle(): Boolean {
        val lastArticleId = dictionaryLocalDataSource.getLastArticleId()
        val newArticle = dictionaryRemoteDataSource.getArticleById(lastArticleId + 1)
        if (newArticle != null)
            return true
        else
            return false
    }

    //Retrofit
    override suspend fun synchronizeSicknessTypesWithServer() {
        try{
            while (checkNewSicknessType()) {
                val lastSicknessTypeId = dictionaryLocalDataSource.getLastSicknessTypeId()
                val sicknessTypesList = dictionaryRemoteDataSource.getSicknessTypesList(
                    offset = lastSicknessTypeId + 1,
                    limit = 50
                )
                sicknessTypesList.forEach {
                    dictionaryLocalDataSource.insertASicknessType(it)
                }
            }
        }
        catch (e: HttpException) {
            Log.w("ApiService", e.message.toString())
        }
        catch (e: Exception) {
            Log.e("ApiService","ApiService unexpected Error")
            Log.e("ApiService", e.message.toString())
        }
    }

    private suspend fun checkNewSicknessType(): Boolean {
        val lastArticleId = dictionaryLocalDataSource.getLastArticleId()
        val newArticle = dictionaryRemoteDataSource.getArticleById(lastArticleId + 1)
        if (newArticle != null)
            return true
        else
            return false
    }

}