package com.example.app_data.animals

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import java.util.UUID


class AnimalsLocalDataSource @Inject constructor(
        private val animalsDao: AnimalsDao
) {
    fun getAllSicknessTypesFlow(): Flow<List<SicknessType>> = animalsDao.getAllSicknessTypes()
    suspend fun getSicknessType(id: Int): SicknessType? = animalsDao.getSicknessType(id)
    //Vaccination
    suspend fun getVaccination(id: UUID): Vaccination? = animalsDao.getVaccination(id)
    suspend fun getVaccinationsByAnimalId(animalId: UUID): List<Vaccination> = animalsDao.getVaccinationsByAnimalId(animalId)
    suspend fun insertVaccination(vaccination: Vaccination) = animalsDao.insertVaccination(vaccination)
    suspend fun getMandatorySicknessTypes(animalType: AnimalType) = animalsDao.getMandatorySicknessTypes(animalType)
    suspend fun deleteVaccination(vaccination: Vaccination) = animalsDao.deleteVaccination(vaccination)
    suspend fun updateVaccination(vaccination: Vaccination) = animalsDao.updateVaccination(vaccination)

    //Sickness
    suspend fun getSickness(id: UUID): Sickness? = animalsDao.getSickness(id)
    suspend fun insertSickness(sickness: Sickness) = animalsDao.insertSickness(sickness)
    suspend fun deleteSickness(sickness: Sickness) = animalsDao.deleteSickness(sickness)
    suspend fun updateSickness(sickness: Sickness) = animalsDao.updateSickness(sickness)

    //MilkYield
    suspend fun getMilkYield(id: UUID): MilkYield? = animalsDao.getMilkYield(id)
    suspend fun insertMilkYield(milkYield: MilkYield) = animalsDao.insertMilkYield(milkYield)
    suspend fun deleteMilkYield(milkYield: MilkYield) = animalsDao.deleteMilkYield(milkYield)
    suspend fun updateMilkYield(milkYield: MilkYield) = animalsDao.updateMilkYield(milkYield)
}

