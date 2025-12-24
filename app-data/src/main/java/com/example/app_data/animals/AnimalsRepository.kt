package com.example.app_data.animals

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject


interface AnimalsRepository {
    val sicknessTypesList: Flow<List<SicknessType>>

    //Vaccination
    suspend fun getVaccination(id: UUID) : Vaccination?
    suspend fun insertVaccination(vaccination: Vaccination)
    suspend fun deleteVaccination(vaccination: Vaccination)
    suspend fun updateVaccination(vaccination: Vaccination)

    //Sickness
    suspend fun getSickness(id: UUID) : Sickness?
    suspend fun insertSickness(sickness: Sickness)
    suspend fun deleteSickness(sickness: Sickness)
    suspend fun updateSickness(sickness: Sickness)
}

class DefaultAnimalsRepository @Inject constructor(
    private val animalsLocalDataSource: AnimalsLocalDataSource
) : AnimalsRepository {
    override val sicknessTypesList: Flow<List<SicknessType>> = animalsLocalDataSource.getAllSicknessTypesFlow()

    //Vaccination
    override suspend fun getVaccination(id: UUID): Vaccination? = animalsLocalDataSource.getVaccination(id)
    override suspend fun insertVaccination(vaccination: Vaccination) = animalsLocalDataSource.insertVaccination(vaccination)
    override suspend fun deleteVaccination(vaccination: Vaccination) = animalsLocalDataSource.deleteVaccination(vaccination)
    override suspend fun updateVaccination(vaccination: Vaccination) = animalsLocalDataSource.updateVaccination(vaccination)

    //Sickness
    override suspend fun getSickness(id: UUID): Sickness? = animalsLocalDataSource.getSickness(id)
    override suspend fun insertSickness(sickness: Sickness) = animalsLocalDataSource.insertSickness(sickness)
    override suspend fun deleteSickness(sickness: Sickness) = animalsLocalDataSource.deleteSickness(sickness)
    override suspend fun updateSickness(sickness: Sickness) = animalsLocalDataSource.updateSickness(sickness)
}