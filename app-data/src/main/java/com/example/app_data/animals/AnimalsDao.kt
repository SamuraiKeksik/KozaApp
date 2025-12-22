package com.example.app_data.animals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface AnimalsDao {

    @Query("SELECT * FROM sickness_types")
    fun getAllSicknessTypes(): Flow<List<SicknessType>>

    @Query("SELECT * FROM vaccinations WHERE id = :id")
    suspend fun getVaccination(id: UUID): Vaccination?

    @Insert
    suspend fun insertSicknessType(sicknessType: SicknessType)

    @Insert
    suspend fun insertVaccination(vaccination: Vaccination)

    @Delete
    suspend fun deleteVaccination(vaccination: Vaccination)
    @Update
    suspend fun updateVaccination(vaccination: Vaccination)
}