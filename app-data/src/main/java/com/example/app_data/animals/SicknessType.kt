 package com.example.app_data.animals

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_data.R

 @Entity(tableName = ("sickness_types"))
data class SicknessType (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val animalType: AnimalType,
    val isVaccinationMandatory: Boolean,
    val revaccinationPeriodInDays: Int,
    val minimalAgeInDays: Int
)

 enum class AnimalType (@StringRes val valueRes: Int){
     ALL(R.string.animal_type_all),
    UNKNOWN(R.string.animal_type_unknown),
     COW(R.string.animal_type_cow),
     GOAT(R.string.animal_type_goat),
     PIG(R.string.animal_type_pig),
     SHEEP(R.string.animal_type_sheep),
     CHICKEN(R.string.animal_type_chicken)
}