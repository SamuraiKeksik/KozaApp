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
    HOLSTEIN_FRIESIAN(R.string.cows_breed_holstein_friesian),
    JERSEY(R.string.cows_breed_jersey),
    AYRSHIRE(R.string.cows_breed_ayrshire),
    SIMMENTAL(R.string.cows_breed_simmental),
    BROWN_SWISS(R.string.cows_breed_brown_swiss),
    HEREFORD(R.string.cows_breed_hereford),
    ABERDEEN_ANGUS(R.string.cows_breed_aberdeen_angus),
    CHAROLAIS(R.string.cows_breed_charolais),
    SALERS(R.string.cows_breed_salers),
    LIMOUSIN(R.string.cows_breed_limousin),
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
    HIDE(R.string.status_hide),
    BREEDING(R.string.status_breeding),
    OTHER(R.string.status_unknown);

    companion object {
        fun valuesList(): List<Status> = entries
    }
}