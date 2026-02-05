package com.example.app_data.dictionary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE category = :articleCategory AND animalType = :animalType")
    fun getArticlesByCategoryAndAnimalType(animalType: AnimalType, articleCategory: ArticleCategory): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE category = :articleCategory")
    fun getArticlesByCategory(articleCategory: ArticleCategory): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM sickness_types EXCEPT SELECT * FROM sickness_types WHERE name = 'Неизвестно'")
    fun getAllSicknessesTypes(): Flow<List<SicknessType>>

    @Query("SELECT * FROM sickness_types WHERE animalType = :animalType")
    fun getSicknessesTypesByAnimalType(animalType: AnimalType): Flow<List<SicknessType>>

    @Query("SELECT id FROM articles ORDER BY id DESC LIMIT 1")
    suspend fun getLastArticleId() : Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT id FROM sickness_types ORDER BY id DESC LIMIT 1")
    suspend fun getLastSicknessTypeId() : Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSicknessType(sicknessType: SicknessType)



}