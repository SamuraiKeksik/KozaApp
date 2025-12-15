package com.example.app_data.animals.goats

import androidx.room.Embedded
import androidx.room.Relation
import com.example.app_data.animals.MilkYield
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.Weight


data class GoatModel (
    @Embedded val goat: GoatEntity,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val vaccinations: List<Vaccination>,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val sicknesses: List<Sickness>,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val weights: List<Weight>,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val milkYields: List<MilkYield>,
)