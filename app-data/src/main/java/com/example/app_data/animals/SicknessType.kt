 package com.example.app_data.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = ("sickness_types"))
data class SicknessType (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val animalType: AnimalType,
)

enum class AnimalType {
    Unknown, Goat, Cow, Pig, Sheep
}