package com.example.app_data.animals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalsDao {

    @Query("SELECT * FROM sickness_types")
    fun getAllSicknessTypesFlow(): Flow<List<SicknessType>>

    @Insert
    fun insertSicknessType(sicknessType: SicknessType)

    @Insert
    fun insertVaccination(vaccination: Vaccination)
}