package com.example.app_data.animals

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject


interface AnimalsRepository {
    val sicknessTypesList: Flow<List<SicknessType>>
    suspend fun getSicknessType(id: Int): SicknessType?

    //Vaccination
    suspend fun getVaccination(id: UUID): Vaccination?

    //suspend fun getVaccinationsByAnimalType(animalType: AnimalType) : List<Vaccination>
    suspend fun insertVaccination(vaccination: Vaccination)
    suspend fun createInitialVaccinations(animalId: UUID, animalAgeInDays: Int, animalType: AnimalType)
    suspend fun deleteVaccination(vaccination: Vaccination)
    suspend fun updateVaccination(vaccination: Vaccination)

    //Sickness
    suspend fun getSickness(id: UUID): Sickness?
    suspend fun insertSickness(sickness: Sickness)
    suspend fun deleteSickness(sickness: Sickness)
    suspend fun updateSickness(sickness: Sickness)

    //MilkYields
    suspend fun getMilkYield(id: UUID): MilkYield?
    suspend fun insertMilkYield(milkYield: MilkYield)
    suspend fun deleteMilkYield(milkYield: MilkYield)
    suspend fun updateMilkYield(milkYield: MilkYield)

    fun getAnimalsCountStream(animalType: AnimalType): Flow<Int>
}

class DefaultAnimalsRepository @Inject constructor(
    private val animalsLocalDataSource: AnimalsLocalDataSource
) : AnimalsRepository {
    override val sicknessTypesList: Flow<List<SicknessType>> =
        animalsLocalDataSource.getAllSicknessTypesFlow()

    override suspend fun getSicknessType(id: Int): SicknessType? =
        animalsLocalDataSource.getSicknessType(id)

    //Vaccination
    override suspend fun getVaccination(id: UUID): Vaccination? =
        animalsLocalDataSource.getVaccination(id)

    //override suspend fun getVaccinationsByAnimalId(animalId: UUID): List<Vaccination> = animalsLocalDataSource.getVaccinationsByAnimalId(animalId)
    override suspend fun insertVaccination(vaccination: Vaccination) =
        animalsLocalDataSource.insertVaccination(vaccination)

    override suspend fun createInitialVaccinations(
        animalId: UUID,
        animalAgeInDays: Int,
        animalType: AnimalType
    ) {
        val mandatorySicknessTypes = animalsLocalDataSource.getMandatorySicknessTypes(animalType)
        mandatorySicknessTypes.forEach {
            var vaccinationDate = LocalDate.now()
            if (it.minimalAgeInDays > animalAgeInDays) {
                vaccinationDate =
                    vaccinationDate.plusDays((it.minimalAgeInDays - animalAgeInDays).toLong())
            }
            animalsLocalDataSource.insertVaccination(
                Vaccination(
                    id = UUID.randomUUID(),
                    animalId = animalId,
                    sicknessTypeId = it.id,
                    date = vaccinationDate.toEpochDay(),
                    medication = "",
                    isPlanned = true,
                )
            )
        }
    }


override suspend fun deleteVaccination(vaccination: Vaccination) =
    animalsLocalDataSource.deleteVaccination(vaccination)

override suspend fun updateVaccination(vaccination: Vaccination) =
    animalsLocalDataSource.updateVaccination(vaccination)

//Sickness
override suspend fun getSickness(id: UUID): Sickness? = animalsLocalDataSource.getSickness(id)
override suspend fun insertSickness(sickness: Sickness) =
    animalsLocalDataSource.insertSickness(sickness)

override suspend fun deleteSickness(sickness: Sickness) =
    animalsLocalDataSource.deleteSickness(sickness)

override suspend fun updateSickness(sickness: Sickness) =
    animalsLocalDataSource.updateSickness(sickness)

//MilkYield
override suspend fun getMilkYield(id: UUID): MilkYield? = animalsLocalDataSource.getMilkYield(id)
override suspend fun insertMilkYield(milkYield: MilkYield) =
    animalsLocalDataSource.insertMilkYield(milkYield)

override suspend fun deleteMilkYield(milkYield: MilkYield) =
    animalsLocalDataSource.deleteMilkYield(milkYield)

override suspend fun updateMilkYield(milkYield: MilkYield) =
    animalsLocalDataSource.updateMilkYield(milkYield)

    override fun getAnimalsCountStream(animalType: AnimalType): Flow<Int> {
        return when (animalType) {
            AnimalType.GOAT -> animalsLocalDataSource.getGoatsCount()
            AnimalType.COW -> animalsLocalDataSource.getCowsCount()
            AnimalType.CHICKEN -> animalsLocalDataSource.getChickensCount()
            else -> flow { emit(0) }
        }
    }
}