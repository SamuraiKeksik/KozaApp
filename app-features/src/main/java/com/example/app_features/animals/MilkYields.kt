package com.example.app_features.animals

import com.example.app_data.animals.MilkYield
import java.time.LocalDate
import java.util.UUID

data class MilkYieldUiState(
    val milkYieldDetails: MilkYieldDetails = MilkYieldDetails(),
    val isEntryValid: Boolean = false
)

data class MilkYieldDetails(
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID = UUID.randomUUID(),
    val amount: String? = "0",
    val date: Long = LocalDate.now().toEpochDay(),
)

fun MilkYieldDetails.toMilkYield() = MilkYield(
    id = id,
    animalId = animalId,
    amount = amount?.toDouble() ?: 0.0,
    date = date,
)

fun MilkYield.toMilkYieldDetails() = MilkYieldDetails(
    id = id,
    animalId = animalId,
    amount = amount.toString(),
    date = date,
)