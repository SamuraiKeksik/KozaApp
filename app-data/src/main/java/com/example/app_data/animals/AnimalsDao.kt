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
    suspend fun insertSicknessType(sicknessType: SicknessType)

    @Insert
    suspend fun insertVaccination(vaccination: Vaccination)
}