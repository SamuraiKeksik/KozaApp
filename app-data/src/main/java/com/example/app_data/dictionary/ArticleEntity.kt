package com.example.app_data.dictionary

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_data.animals.AnimalType

@Entity(tableName = ("articles"))
data class ArticleEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val animalType: AnimalType,
    val category: ArticleCategory
)

enum class ArticleCategory {
    UNKNOWN, FEEDING, BREEDING
}