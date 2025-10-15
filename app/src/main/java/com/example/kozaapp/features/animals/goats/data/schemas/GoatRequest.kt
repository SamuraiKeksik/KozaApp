package com.example.kozaapp.features.animals.goats.data.schemas

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kozaapp.features.animals.model.Breed
import com.example.kozaapp.features.animals.model.Gender
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.animals.model.Status

data class GoatRequest(
    val name: String,
    val gender: String,
    val breed: String,
    val status: String,
    val weight: Int?,
    val birthDate: String?,
    val description: String?,
)

fun Goat.toGoatRequest(): GoatRequest {
    return GoatRequest(
        name = this.name,
        gender = this.gender.toString(),
        breed = this.breed.toString(),
        status = this.status.toString(),
        weight = this.weight,
        birthDate = this.birthDate,
        description = this.description,
    )
}