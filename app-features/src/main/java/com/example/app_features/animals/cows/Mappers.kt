package com.example.app_features.animals.cows

import com.example.app_data.animals.cows.CowEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun CowDetails.toCow(): CowEntity {
    fun formatDate(date: String): Long {
        try{
            DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(date)
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")).toEpochDay()
        } catch (e: Exception) {
            return LocalDate.now().toEpochDay()
        }
    }

    return CowEntity(
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

fun CowEntity.toCowUiState(isEntryValid: Boolean = false): CowUiState = CowUiState(
    cowDetails = this.toCowDetails(),
    isEntryValid = isEntryValid
)

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)


fun CowEntity.toCowDetails(): CowDetails = CowDetails(
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