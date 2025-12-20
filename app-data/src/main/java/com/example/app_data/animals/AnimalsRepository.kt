package com.example.app_data.animals

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface AnimalsRepository {
    val sicknessTypesList: Flow<List<SicknessType>>
    suspend fun insertVaccination(vaccination: Vaccination)
}

class DefaultAnimalsRepository @Inject constructor(
    private val animalsLocalDataSource: AnimalsLocalDataSource
) : AnimalsRepository {
    override val sicknessTypesList: Flow<List<SicknessType>> = animalsLocalDataSource.getAllSicknessTypesFlow()
    override suspend fun insertVaccination(vaccination: Vaccination) =
        animalsLocalDataSource.insertVaccination(vaccination)
}