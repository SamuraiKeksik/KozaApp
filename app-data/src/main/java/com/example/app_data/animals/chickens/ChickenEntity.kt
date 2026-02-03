package com.example.app_data.animals.chickens

import androidx.annotation.StringRes
import java.util.UUID
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_data.R
import com.example.app_data.animals.Gender

@Entity(tableName = "chickens")
data class ChickenEntity(
    @PrimaryKey()
    val id: UUID = UUID.randomUUID(),
    val motherId: UUID?,
    val fatherId: UUID?,
    val name: String,
    val gender: Gender,
    val breed: ChickenBreed,
    val status: Status,
    val weight: Int,
    val birthDate: Long,
    val description: String,

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
)


enum class ChickenBreed(
    @StringRes val valueRes: Int
) {
    LEGHORN(R.string.chickens_breed_leghorn),
    LOHMANN_BROWN(R.string.chickens_breed_lohmann_brown),
    HYSEX_WHITE(R.string.chickens_breed_hysex_white),
    RHODE_ISLAND_RED(R.string.chickens_breed_rhode_island_red),
    MINORCA(R.string.chickens_breed_minorca),
    COBB_500(R.string.chickens_breed_cobb_500),
    ROSS_308(R.string.chickens_breed_ross_308),
    ARBOR_ACRES(R.string.chickens_breed_arbor_acres),
    CORNISH_CROSS(R.string.chickens_breed_cornish_cross),
    ORPINGTON(R.string.chickens_breed_orpington),
    OTHER(R.string.breed_unknown);

    companion object {
        fun valuesList(): List<ChickenBreed> = entries
    }
}

enum class Status(
    @StringRes val valueRes: Int
){
    MEAT(R.string.status_meat),
    BREEDING(R.string.status_breeding),
    LAYING_HEN(R.string.status_laying_hen),
    OTHER(R.string.status_unknown);

    companion object {
        fun valuesList(): List<Status> = entries
    }
}