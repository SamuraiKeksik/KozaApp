package com.example.app_data.animals

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "sicknesses")
data class  Sickness (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID,
    val sicknessTypeId: Int,
    val startDate: Long,
    val endDate: Long?,
)