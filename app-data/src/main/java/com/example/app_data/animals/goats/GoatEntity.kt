package com.example.app_data.animals.goats

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import java.util.UUID
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_data.R

@Entity(tableName = "goats")
data class GoatEntity(
    @PrimaryKey()
    val id: UUID = UUID.randomUUID(),
    val motherId: UUID?,
    val fatherId: UUID?,
    val name: String,
    val gender: Gender,
    val breed: Breed,
    val status: Status,
    val weight: Int,
    val birthDate: Long,
    val description: String,

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
)

enum class Gender(
    @StringRes val valueRes: Int
) {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    UNKNOWN(R.string.gender_unknown);

    companion object {
        fun valuesList(): List<Gender> = entries
    }
}

enum class Breed(
    @StringRes val valueRes: Int
) {
    ZAANENSKAYA(R.string.breed_zaanenskaya),
    NUBIAN(R.string.breed_nubian),
    LA_MANCHA(R.string.breed_la_mancha),
    TOGGENBURGSKAYA(R.string.breed_toggenburgskaya),
    ALPINE(R.string.breed_alpine),
    GORNOALTAYSKAYA(R.string.breed_gornoaltayskaya),
    ORENBURG(R.string.breed_orenburg),
    ANGORA(R.string.breed_angora),
    CAMEROONIAN(R.string.breed_cameroonian),
    OTHER(R.string.breed_unknown);

    companion object {
        fun valuesList(): List<Breed> = entries
    }
}

enum class Status(
    @StringRes val valueRes: Int
){
    MILK(R.string.status_milk),
    MEAT(R.string.status_meat),
    WOOL(R.string.status_wool),
    BREEDING(R.string.status_breeding),
    OTHER(R.string.status_unknown);

    companion object {
        fun valuesList(): List<Status> = entries
    }
}