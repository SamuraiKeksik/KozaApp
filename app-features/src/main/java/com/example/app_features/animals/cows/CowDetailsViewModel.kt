package com.example.app_features.animals.cows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.AnimalsRepository
import com.example.app_data.animals.MilkYield
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.SicknessType
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.cows.CowRepository
import com.example.app_features.animals.MilkYieldDetails
import com.example.app_features.animals.MilkYieldUiState
import com.example.app_features.animals.SicknessDetails
import com.example.app_features.animals.SicknessUiState
import com.example.app_features.animals.VaccinationDetails
import com.example.app_features.animals.VaccinationUiState
import com.example.app_features.animals.toMilkYield
import com.example.app_features.animals.toMilkYieldDetails
import com.example.app_features.animals.toSickness
import com.example.app_features.animals.toSicknessDetails
import com.example.app_features.animals.toVaccination
import com.example.app_features.animals.toVaccinationDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cowRepository: CowRepository,
    private val animalsRepository: AnimalsRepository
) : ViewModel() {
    private val cowId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

    var sicknessTypesList: StateFlow<SicknessTypesUiState> =
        animalsRepository.sicknessTypesList.map {
            SicknessTypesUiState(it)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            SicknessTypesUiState()
        )

    var uiState: StateFlow<CowDetailsUiState> =
        cowRepository.getCowModel(cowId)
            .filterNotNull()
            .map {
                CowDetailsUiState(
                    cowDetails = it.cow.toCowDetails().copy(
                        motherName = it.cow.motherId?.let { id -> cowRepository.getCowName(id) },
                        fatherName = it.cow.fatherId?.let { id -> cowRepository.getCowName(id) },
                    ),
                    cowVaccinations = it.vaccinations.filter { vac -> !vac.isPlanned }.sortedBy { vac -> vac.date },
                    cowSicknesses = it.sicknesses,
                    cowMilkYields = it.milkYields
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CowDetailsUiState()
            )

    var vaccinationUiState by mutableStateOf(
        VaccinationUiState(
            vaccinationDetails = VaccinationDetails(
                animalId = cowId
            )
        )
    )
        private set
    var sicknessUiState by mutableStateOf(SicknessUiState(sicknessDetails = SicknessDetails(animalId = cowId)))
        private set
    var milkYieldUiState by mutableStateOf(
        MilkYieldUiState(
            milkYieldDetails = MilkYieldDetails(
                animalId = cowId
            )
        )
    )
        private set

    suspend fun deleteCow() {
        cowRepository.deleteCow(uiState.value.cowDetails.toCow())
    }

    //Vaccinations
    fun updateVaccinationUiState(vaccinationDetails: VaccinationDetails) {
        vaccinationUiState = VaccinationUiState(
            vaccinationDetails = vaccinationDetails,
            isEntryValid =
                vaccinationDetails.sicknessName.isNotBlank() &&
                        vaccinationDetails.sicknessTypeId != 0 &&
                        vaccinationDetails.medication.isNotBlank()
        )
    }

    suspend fun getVaccination(id: UUID) {
        val vaccination = animalsRepository.getVaccination(id)
        if (vaccination != null) {
            val sickness =
                sicknessTypesList.value.sicknessTypesList.find { it.id == vaccination.sicknessTypeId }
            updateVaccinationUiState(
                vaccination.toVaccinationDetails().copy(sicknessName = sickness?.name ?: "")
            )
        }
    }

    suspend fun insertVaccination() {
        if (vaccinationUiState.isEntryValid) {
            animalsRepository.insertVaccination(vaccinationUiState.vaccinationDetails.toVaccination())
        }
        //Обнуляем поля
        clearVaccinationUiState()
    }

    suspend fun updateVaccination() {
        if (vaccinationUiState.isEntryValid) {
            animalsRepository.updateVaccination(vaccinationUiState.vaccinationDetails.toVaccination())
        }
        clearVaccinationUiState()
    }

    suspend fun deleteVaccination() {
        val vaccination = animalsRepository.getVaccination(vaccinationUiState.vaccinationDetails.id)
        if (vaccination != null) {
            animalsRepository.deleteVaccination(vaccination)
        }
        clearVaccinationUiState()
    }

    fun clearVaccinationUiState() =
        updateVaccinationUiState(vaccinationDetails = VaccinationDetails(animalId = cowId))


    //Sicknesses
    fun updateSicknessUiState(sicknessDetails: SicknessDetails) {
        sicknessUiState = SicknessUiState(
            sicknessDetails = sicknessDetails,
            isEntryValid =
                sicknessDetails.sicknessName.isNotBlank() &&
                        sicknessDetails.sicknessTypeId != 0
        )
    }

    suspend fun getSickness(id: UUID) {
        val sickness = animalsRepository.getSickness(id)
        if (sickness != null) {
            val sicknessType = sicknessTypesList.value.sicknessTypesList.find { it.id == sickness.sicknessTypeId }
            updateSicknessUiState(
                sickness.toSicknessDetails().copy(sicknessName = sicknessType?.name ?: "")
            )
        }
    }

    suspend fun insertSickness() {
        if (sicknessUiState.isEntryValid) {
            animalsRepository.insertSickness(sicknessUiState.sicknessDetails.toSickness())
        }
        //Обнуляем поля
        clearSicknessUiState()
    }

    suspend fun updateSickness() {
        if (sicknessUiState.isEntryValid) {
            animalsRepository.updateSickness(sicknessUiState.sicknessDetails.toSickness())
        }
        clearSicknessUiState()
    }

    suspend fun deleteSickness() {
        val sickness = animalsRepository.getSickness(sicknessUiState.sicknessDetails.id)
        if (sickness != null) {
            animalsRepository.deleteSickness(sickness)
        }
        clearSicknessUiState()
    }

    fun clearSicknessUiState() =
        updateSicknessUiState(sicknessDetails = SicknessDetails(animalId = cowId))


    //MilkYields
    fun updateMilkYieldUiState(milkYieldDetails: MilkYieldDetails) {
        milkYieldUiState = MilkYieldUiState(
            milkYieldDetails = milkYieldDetails,
            isEntryValid = milkYieldDetails.amount?.toDoubleOrNull() != null
        )
    }

    suspend fun getMilkYield(id: UUID) {
        val milkYield = animalsRepository.getMilkYield(id)
        if (milkYield != null) updateMilkYieldUiState(milkYield.toMilkYieldDetails())
    }

    suspend fun insertMilkYield() {
        if (milkYieldUiState.isEntryValid) {
            animalsRepository.insertMilkYield(milkYieldUiState.milkYieldDetails.toMilkYield())
        }
        //Обнуляем поля
        clearMilkYieldUiState()
    }

    suspend fun updateMilkYield() {
        if (milkYieldUiState.isEntryValid) {
            animalsRepository.updateMilkYield(milkYieldUiState.milkYieldDetails.toMilkYield())
        }
        clearMilkYieldUiState()
    }

    suspend fun deleteMilkYield() {
        val milkYield = animalsRepository.getMilkYield(milkYieldUiState.milkYieldDetails.id)
        if (milkYield != null) {
            animalsRepository.deleteMilkYield(milkYield)
        }
        clearMilkYieldUiState()
    }

    fun clearMilkYieldUiState() = updateMilkYieldUiState(milkYieldDetails = MilkYieldDetails(animalId = cowId))

    //------------------------------------\\

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class CowDetailsUiState(
    val cowDetails: CowDetails = CowDetails(),
    val cowVaccinations: List<Vaccination> = emptyList(),
    val cowSicknesses: List<Sickness> = emptyList(),
    val cowMilkYields: List<MilkYield> = emptyList(),
    val selectedVaccinationId: UUID = UUID.randomUUID(),
    val selectedSicknessId: UUID = UUID.randomUUID()
)

data class SicknessTypesUiState(val sicknessTypesList: List<SicknessType> = emptyList())

