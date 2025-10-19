package com.example.kozaapp.features.animals.goats.data.schemas

import com.example.kozaapp.features.animals.model.Breed
import com.example.kozaapp.features.animals.model.Gender
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.animals.model.Status
import kotlin.Int

data class GoatResponse(
    val id: String,
    val name: String,
    val gender: String,
    val breed: String,
    val status: String,
    val weight: Int,
    val birthDate: String,
    val description: String,
)

fun GoatResponse.toGoatModel(): Goat {
    return Goat(
        id = 0,
        name = this.name,
        gender = Gender.valueOf(this.gender),
        breed = Breed.valueOf(this.breed),
        status = Status.valueOf(this.status),
        weight = this.weight,
        birthDate = this.birthDate,
        description = this.description,

        serverId = this.id,
        isEdited = false,
        isDeleted = false,
    )
}
