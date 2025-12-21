package com.example.app_data.animals

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject


interface AnimalsRepository {
    val sicknessTypesList: Flow<List<SicknessType>>
    suspend fun getVaccination(id: UUID) : Vaccination?
    suspend fun insertVaccination(vaccination: Vaccination)
    suspend fun deleteVaccination(vaccination: Vaccination)
}

class DefaultAnimalsRepository @Inject constructor(
    private val animalsLocalDataSource: AnimalsLocalDataSource
) : AnimalsRepository {
    override val sicknessTypesList: Flow<List<SicknessType>> = animalsLocalDataSource.getAllSicknessTypesFlow()
    override suspend fun getVaccination(id: UUID): Vaccination? = animalsLocalDataSource.getVaccination(id)

    override suspend fun insertVaccination(vaccination: Vaccination) =
        animalsLocalDataSource.insertVaccination(vaccination)

    override suspend fun deleteVaccination(vaccination: Vaccination) = animalsLocalDataSource.deleteVaccination(vaccination)
}