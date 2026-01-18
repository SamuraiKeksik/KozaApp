package com.example.app_data.animals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface AnimalsDao {

    @Query("SELECT * FROM sickness_types")
    fun getAllSicknessTypes(): Flow<List<SicknessType>>
    @Query("SELECT * FROM sickness_types WHERE id = :id")
    suspend fun getSicknessType(id: Int): SicknessType?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSicknessType(sicknessType: SicknessType)

    //Vaccination
    @Query("SELECT * FROM vaccinations WHERE id = :id")
    suspend fun getVaccination(id: UUID): Vaccination?

    @Query("SELECT * FROM vaccinations WHERE animalId = :animalId")
    suspend fun getVaccinationsByAnimalId(animalId: UUID): List<Vaccination>
    @Insert
    suspend fun insertVaccination(vaccination: Vaccination)
    @Delete
    suspend fun deleteVaccination(vaccination: Vaccination)
    @Update
    suspend fun updateVaccination(vaccination: Vaccination)

    //Sickness

    @Query("SELECT * FROM sicknesses WHERE id = :id")
    suspend fun getSickness(id: UUID): Sickness?
    @Insert
    suspend fun insertSickness(sickness: Sickness)
    @Delete
    suspend fun deleteSickness(sickness: Sickness)
    @Update
    suspend fun updateSickness(sickness: Sickness)

    //MilkYield
    @Query("SELECT * FROM milk_yields WHERE id = :id")
    suspend fun getMilkYield(id: UUID): MilkYield?
    @Insert
    suspend fun insertMilkYield(milkYield: MilkYield)
    @Delete
    suspend fun deleteMilkYield(milkYield: MilkYield)
    @Update
    suspend fun updateMilkYield(milkYield: MilkYield)
}