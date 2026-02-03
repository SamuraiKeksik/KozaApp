package com.example.app_features.animals.chickens

import com.example.app_data.animals.chickens.ChickenEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun ChickenDetails.toChicken(): ChickenEntity {
    fun formatDate(date: String): Long {
        try{
            DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(date)
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")).toEpochDay()
        } catch (e: Exception) {
            return LocalDate.now().toEpochDay()
        }
    }

    return ChickenEntity(
        id = id,
        motherId = motherId,
        fatherId = fatherId,
        name = name,
        gender = gender,
        birthDate = birthDate.toEpochDay(),
        description = description,
        breed = breed,
        status = status,
        weight = weight.toIntOrNull() ?: 0,

        isEdited = isEdited,
        isDeleted = isDeleted,

        //serverId = serverId,
    )
}

fun ChickenEntity.toChickenUiState(isEntryValid: Boolean = false): ChickenUiState = ChickenUiState(
    chickenDetails = this.toChickenDetails(),
    isEntryValid = isEntryValid
)

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)


fun ChickenEntity.toChickenDetails(): ChickenDetails = ChickenDetails(
    id = id,
    motherId = motherId,
    fatherId = fatherId,
    name = name,
    gender = gender,
    birthDate = LocalDate.ofEpochDay(birthDate),
    description = description,
    breed = breed,
    status = status,
    weight = weight.toString(),

    isEdited = isEdited,
    isDeleted = isDeleted,
    //serverId = serverId,
)