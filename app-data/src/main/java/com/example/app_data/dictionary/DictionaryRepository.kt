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
    private val dictionaryLocalDataSource: DictionaryLocalDataSource,
    private val dictionaryRemoteDataSource: DictionaryRemoteDataSource
) : DictionaryRepository {
    override fun getArticlesByCategoryAndAnimalType(animalType: AnimalType, articleCategory: ArticleCategory): Flow<List<ArticleEntity>> {
        if (animalType == AnimalType.ALL && articleCategory != ArticleCategory.ALL) {
            return dictionaryLocalDataSource.getArticlesByCategoryFlow(articleCategory = articleCategory)
        }
        if (animalType == AnimalType.ALL && articleCategory == ArticleCategory.ALL){
            return dictionaryLocalDataSource.getAllArticlesFlow()
        }
        return dictionaryLocalDataSource.getArticlesByCategoryAndAnimalTypeFlow(animalType = animalType, articleCategory = articleCategory)
    }
    override fun getSicknessesTypesByAnimalType(animalType: AnimalType): Flow<List<SicknessType>> {
        if (animalType == AnimalType.ALL) {
            return dictionaryLocalDataSource.getAllSicknessesTypesFlow()
        }
        return dictionaryLocalDataSource.getSicknessesTypesByAnimalTypeFlow(animalType = animalType)
    }
}