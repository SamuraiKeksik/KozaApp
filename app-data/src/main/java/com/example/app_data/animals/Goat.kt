package com.example.app_data.animals

import java.util.UUID
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goats")
data class Goat(
    @PrimaryKey()
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val gender: Gender,
    val breed: Breed,
    val status: Status,
    val weight: Int,
    val birthDate: String?,
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

enum class Breed(){
    ZAANENSKAYA, NUBIAN, LA_MANCHA, TOGGENBURGSKAYA, ALPINE,
    GORNOALTAYSKAYA, ORENBURG, ANGORA, CAMEROONIAN, OTHER;

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