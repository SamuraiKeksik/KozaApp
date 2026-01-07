package com.example.app_data.dictionary

import androidx.room.Dao
import androidx.room.Query
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {

    @Query("SELECT * FROM articles WHERE category = :articleCategory AND animalType = :animalType")
    fun getArticlesByCategory(animalType: AnimalType, articleCategory: ArticleCategory): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM sickness_types WHERE animalType = :animalType")
    fun getSicknessesTypesByAnimalType(animalType: AnimalType): Flow<List<SicknessType>>

}