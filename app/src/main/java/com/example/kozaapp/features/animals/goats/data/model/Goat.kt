package com.example.kozaapp.features.animals.model

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kozaapp.R

@Entity(tableName = "goats")
data class Goat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val gender: Gender,
    val breed: Breed,
    val status: Status,
    val weight: Int,
    val birthDate: String?,
    val description: String,

    val needsSync: Boolean = false,
    val isDeleted: Boolean = false,
    val serverId: String? = null,
)

enum class Gender(@StringRes val labelResId: Int) {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    UNKNOWN(R.string.gender_unknown);

    companion object {
        fun valuesList(): List<Gender> = entries
    }
}

enum class Breed(@StringRes val labelResId: Int){
    ZAANENSKAYA(R.string.zaanenskaya),
    NUBIAN(R.string.nubian),
    LA_MANCHA(R.string.la_Mancha),
    TOGGENBURGSKAYA(R.string.toggenburgskaya),
    ALPINE(R.string.alpine),
    GORNOALTAYSKAYA(R.string.gornoaltayskaya),
    ORENBURG(R.string.orenburg),
    ANGORA(R.string.angora),
    CAMEROONIAN(R.string.cameroonian),
    OTHER(R.string.other);

    companion object {
        fun valuesList(): List<Breed> = Breed.entries
    }
}

enum class Status(@StringRes val labelResId: Int){
    MILK(R.string.milk),
    MEAT(R.string.meat),
    WOOL(R.string.wool),
    BREEDING(R.string.breeding),
    OTHER(R.string.other);

    companion object {
        fun valuesList(): List<Status> = Status.entries
    }
}