package com.example.app_data.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "weights")
data class Weight (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID,
    val weight: Double, //kg
    val data: Date,
)