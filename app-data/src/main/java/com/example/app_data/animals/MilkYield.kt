package com.example.app_data.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "milk_yields")
data class MilkYield (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID,
    val amount: Double, //litres
    val date: Long
)