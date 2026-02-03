package com.example.app_data.animals.chickens

import androidx.room.Embedded
import androidx.room.Relation
import com.example.app_data.animals.MilkYield
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.Weight


data class ChickenModel (
    @Embedded val chicken: ChickenEntity,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val vaccinations: List<Vaccination>,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val sicknesses: List<Sickness>,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val weights: List<Weight>,

    @Relation(parentColumn = "id", entityColumn = "animalId")
    val milkYields: List<MilkYield>,
)