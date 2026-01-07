package com.example.app_data.dictionary

import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class DictionaryLocalDataSource @Inject constructor(
        private val dictionaryDao: DictionaryDao
) {
    fun getArticlesByCategoryFlow(animalType: AnimalType, articleCategory: ArticleCategory,): Flow<List<ArticleEntity>> =
        dictionaryDao.getArticlesByCategory(animalType, articleCategory)
    fun getSicknessesTypesByAnimalTypeFlow(animalType: AnimalType): Flow<List<SicknessType>> =
        dictionaryDao.getSicknessesTypesByAnimalType(animalType)
}

