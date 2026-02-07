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

    suspend fun synchronizeArticlesWithServer(): Int
    suspend fun synchronizeSicknessTypesWithServer(): Int

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
    override suspend fun synchronizeArticlesWithServer(): Int {
        var articlesCount = 0
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
                articlesCount += articlesList.count()
            }
            return articlesCount
        } catch (e: HttpException) {
            Log.w("ApiService", e.message.toString())
            return articlesCount
        }
        catch (e: Exception) {
            Log.e("ApiService","ApiService unexpected Error")
            Log.e("ApiService", e.message.toString())
            return articlesCount
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
    override suspend fun synchronizeSicknessTypesWithServer(): Int {
        var sicknessTypesCount = 0
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
                sicknessTypesCount += sicknessTypesList.count()
            }
            return sicknessTypesCount
        }
        catch (e: HttpException) {
            Log.w("ApiService", e.message.toString())
            return sicknessTypesCount
        }
        catch (e: Exception) {
            Log.e("ApiService","ApiService unexpected Error")
            Log.e("ApiService", e.message.toString())
            return sicknessTypesCount
        }
    }

    private suspend fun checkNewSicknessType(): Boolean {
        val lastSicknessTypeId = dictionaryLocalDataSource.getLastSicknessTypeId()
        val newSicknessType = dictionaryRemoteDataSource.getSicknessTypeById(lastSicknessTypeId + 1)
        if (newSicknessType != null)
            return true
        else
            return false
    }

}