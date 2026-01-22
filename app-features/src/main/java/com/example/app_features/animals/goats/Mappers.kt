package com.example.app_features.animals.goats

import com.example.app_data.animals.goats.Breed
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_data.animals.goats.Status
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun GoatDetails.toGoat(): GoatEntity {
    val enumGender = runCatching {
        Gender.valueOf(gender.uppercase())
    }.getOrDefault(Gender.UNKNOWN)

    val enumBreed = runCatching {
        Breed.valueOf(breed.uppercase())
    }.getOrDefault(Breed.OTHER)

    val enumStatus = runCatching {
        Status.valueOf(status.uppercase())
    }.getOrDefault(Status.OTHER)

    fun formatDate(date: String): Long {
        try{
            DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(date)
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")).toEpochDay()
        } catch (e: Exception) {
            return LocalDate.now().toEpochDay()
        }
    }

    return GoatEntity(
        id = id,
        motherId = motherId,
        fatherId = fatherId,
        name = name,
        gender = enumGender,
        birthDate = formatDate(birthDate),
        description = description,
        breed = enumBreed,
        status = enumStatus,
        weight = weight.toIntOrNull() ?: 0,

        isEdited = isEdited,
        isDeleted = isDeleted,

        //serverId = serverId,
    )
}

fun GoatEntity.toGoatUiState(isEntryValid: Boolean = false): GoatUiState = GoatUiState(
    goatDetails = this.toGoatDetails(),
    isEntryValid = isEntryValid
)

fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)


fun GoatEntity.toGoatDetails(): GoatDetails = GoatDetails(
    id = id,
    motherId = motherId,
    fatherId = fatherId,
    name = name,
    gender = gender.toString(),
    birthDate = LocalDate.ofEpochDay(birthDate).toString(),
    description = description,
    breed = breed.toString(),
    status = status.toString(),
    weight = weight.toString(),

    isEdited = isEdited,
    isDeleted = isDeleted,
    //serverId = serverId,
)