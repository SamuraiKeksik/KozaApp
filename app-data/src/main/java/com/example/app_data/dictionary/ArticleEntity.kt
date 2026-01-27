package com.example.app_data.dictionary

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_data.R
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

enum class ArticleCategory(@StringRes val valueRes: Int) {
    UNKNOWN(R.string.category_unknown),
    ALL(R.string.category_all),
    FEEDING(R.string.category_feeding),
    BREEDING(R.string.category_breeding),
}