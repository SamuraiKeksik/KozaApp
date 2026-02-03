package com.example.app_features.animals

import com.example.app_data.animals.Vaccination
import java.time.LocalDate
import java.util.UUID

data class VaccinationUiState(
    val vaccinationDetails: VaccinationDetails = VaccinationDetails(),
    val isEntryValid: Boolean = false
)

data class VaccinationDetails(
    val id: UUID = UUID.randomUUID(),
    val date: Long = LocalDate.now().toEpochDay(),
    val sicknessName: String = "",
    val animalId: UUID = UUID.randomUUID(),
    val sicknessTypeId: Int = 0,
    val medication: String = "",
)

fun VaccinationDetails.toVaccination() = Vaccination(
    id = id,
    sicknessTypeId = sicknessTypeId,
    animalId = animalId,
    medication = medication,
    date = date,
    isPlanned = false,
)

fun Vaccination.toVaccinationDetails() = VaccinationDetails(
    id = id,
    sicknessTypeId = sicknessTypeId,
    animalId = animalId,
    medication = medication,
    date = date
)