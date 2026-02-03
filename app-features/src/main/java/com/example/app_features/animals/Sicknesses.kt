package com.example.app_features.animals

import com.example.app_data.animals.Sickness
import java.time.LocalDate
import java.util.UUID

data class SicknessUiState(
    val sicknessDetails: SicknessDetails = SicknessDetails(),
    val isEntryValid: Boolean = false
)

data class SicknessDetails(
    val id: UUID = UUID.randomUUID(),
    val startDate: Long = LocalDate.now().toEpochDay(),
    val endDate: Long? = null,
    val sicknessName: String = "",
    val animalId: UUID = UUID.randomUUID(),
    val sicknessTypeId: Int = 0,
)

fun SicknessDetails.toSickness() = Sickness(
    id = id,
    startDate = startDate,
    endDate = endDate,
    sicknessTypeId = sicknessTypeId,
    animalId = animalId,
)

fun Sickness.toSicknessDetails() = SicknessDetails(
    id = id,
    startDate = startDate,
    endDate = endDate,
    sicknessTypeId = sicknessTypeId,
    animalId = animalId,
)