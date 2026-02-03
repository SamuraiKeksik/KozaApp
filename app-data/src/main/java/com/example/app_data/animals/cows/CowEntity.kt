package com.example.app_data.animals.cows

import androidx.annotation.StringRes
import java.util.UUID
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_data.R
import com.example.app_data.animals.Gender

@Entity(tableName = "cows")
data class CowEntity(
    @PrimaryKey()
    val id: UUID = UUID.randomUUID(),
    val motherId: UUID?,
    val fatherId: UUID?,
    val name: String,
    val gender: Gender,
    val breed: CowBreed,
    val status: Status,
    val weight: Int,
    val birthDate: Long,
    val description: String,

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
)


enum class CowBreed(
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
        fun valuesList(): List<CowBreed> = entries
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