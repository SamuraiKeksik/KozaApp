package com.example.app_data.animals.goats

import androidx.annotation.StringRes
import java.util.UUID
import androidx.room.Entity
import androidx.room.PrimaryKey

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

enum class Gender() {
    MALE, FEMALE, UNKNOWN;

    companion object {
        fun valuesList(): List<Gender> = entries
    }
}

enum class Breed(
    val value: String
) {
    ZAANENSKAYA("Зааненская"),
    NUBIAN("Нубийская"),
    LA_MANCHA("Ла Манча"),
    TOGGENBURGSKAYA("Тоггенбургская"),
    ALPINE("Альпийская"),
    GORNOALTAYSKAYA("Горно-алтайская"),
    ORENBURG("Оренбургская"),
    ANGORA("Ангорская"),
    CAMEROONIAN("Камерунская"),
    OTHER("Другая");

    companion object {
        fun valuesList(): List<Breed> = entries
    }
}

enum class Status(){
    MILK, MEAT, WOOL, BREEDING, OTHER;

    companion object {
        fun valuesList(): List<Status> = entries
    }
}