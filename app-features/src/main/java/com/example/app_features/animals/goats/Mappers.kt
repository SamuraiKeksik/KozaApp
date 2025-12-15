package com.example.app_features.animals.goats

import com.example.app_data.animals.goats.Breed
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_data.animals.goats.Status

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

    return GoatEntity(
        id = id,
        name = name,
        gender = enumGender,
        birthDate = birthDate,
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

fun GoatEntity.toGoatDetails(): GoatDetails = GoatDetails(
    id = id,
    name = name,
    gender = gender.toString(),
    birthDate = birthDate,
    description = description,
    breed = breed.toString(),
    status = status.toString(),
    weight = weight.toString(),

    isEdited = isEdited,
    isDeleted = isDeleted,
    //serverId = serverId,
)