package com.example.app_features.animals

import com.example.app_data.animals.SicknessEntity
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

fun SicknessDetails.toSickness() = SicknessEntity(
    id = id,
    startDate = startDate,
    endDate = endDate,
    sicknessTypeId = sicknessTypeId,
    animalId = animalId,
)

fun SicknessEntity.toSicknessDetails() = SicknessDetails(
    id = id,
    startDate = startDate,
    endDate = endDate,
    sicknessTypeId = sicknessTypeId,
    animalId = animalId,
)