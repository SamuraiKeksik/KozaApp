package com.example.app_data.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "vaccinations")
data class Vaccination (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    //val sickness_Type: SicknessType,
    val animalId: UUID,
    val date: Date,
    val medication: String,
)