package com.example.kozaapp.features.animals.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goats")
data class Goat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val gender: String,
    val birthDate: String,
    val description: String,

)

