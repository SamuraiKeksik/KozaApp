package com.example.app_data.animals

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import java.util.UUID


class AnimalsLocalDataSource @Inject constructor(
        private val animalsDao: AnimalsDao
) {
    fun getAllSicknessTypesFlow(): Flow<List<SicknessType>> = animalsDao.getAllSicknessTypes()
    suspend fun getVaccination(id: UUID): Vaccination? = animalsDao.getVaccination(id)
    suspend fun insertVaccination(vaccination: Vaccination) = animalsDao.insertVaccination(vaccination)
    suspend fun deleteVaccination(vaccination: Vaccination) = animalsDao.deleteVaccination(vaccination)
}

