package com.example.app_data.animals

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class AnimalsLocalDataSource @Inject constructor(
        private val animalsDao: AnimalsDao
) {
    fun getAllSicknessTypesFlow(): Flow<List<SicknessType>> = animalsDao.getAllSicknessTypesFlow()
    suspend fun insertVaccination(vaccination: Vaccination) = animalsDao.insertVaccination(vaccination)
}
