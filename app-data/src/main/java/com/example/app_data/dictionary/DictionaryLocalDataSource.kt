package com.example.app_data.dictionary

import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class DictionaryLocalDataSource @Inject constructor(
        private val dictionaryDao: DictionaryDao
) {
    fun getAllArticlesFlow(): Flow<List<ArticleEntity>> =
        dictionaryDao.getAllArticles()

    fun getArticlesByCategoryAndAnimalTypeFlow(animalType: AnimalType, articleCategory: ArticleCategory,): Flow<List<ArticleEntity>> =
        dictionaryDao.getArticlesByCategoryAndAnimalType(animalType, articleCategory)
    fun getArticlesByCategoryFlow(articleCategory: ArticleCategory,): Flow<List<ArticleEntity>> =
        dictionaryDao.getArticlesByCategory(articleCategory)
    fun getAllSicknessesTypesFlow(): Flow<List<SicknessType>> =
        dictionaryDao.getAllSicknessesTypes()
    fun getSicknessesTypesByAnimalTypeFlow(animalType: AnimalType): Flow<List<SicknessType>> =
        dictionaryDao.getSicknessesTypesByAnimalType(animalType)
}

