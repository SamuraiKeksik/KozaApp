package com.example.app_data.dictionary

import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface DictionaryRepository {
    fun getArticlesByCategoryAndAnimalType(animalType: AnimalType, articleCategory: ArticleCategory) : Flow<List<ArticleEntity>>
    fun getSicknessesTypesByAnimalType(animalType: AnimalType) : Flow<List<SicknessType>>
}

class DefaultDictionaryRepository @Inject constructor(
    private val dictionaryLocalDataSource: DictionaryLocalDataSource
) : DictionaryRepository {
    override fun getArticlesByCategoryAndAnimalType(animalType: AnimalType, articleCategory: ArticleCategory): Flow<List<ArticleEntity>> {
        return dictionaryLocalDataSource.getArticlesByCategoryFlow(animalType = animalType, articleCategory = articleCategory)
    }
    override fun getSicknessesTypesByAnimalType(animalType: AnimalType): Flow<List<SicknessType>> {
        return dictionaryLocalDataSource.getSicknessesTypesByAnimalTypeFlow(animalType = animalType)
    }
}