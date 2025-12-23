package com.example.app_features.animals.goats

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
import com.example.app_data.animals.goats.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GoatDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatRepository: GoatRepository,
    private val animalsRepository: AnimalsRepository
) : ViewModel() {
    private val goatId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

    var sicknessTypesList: StateFlow<SicknessTypesUiState> =
        animalsRepository.sicknessTypesList.map {
            SicknessTypesUiState(it)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            SicknessTypesUiState()
        )

    var uiState: StateFlow<GoatDetailsUiState> =
        goatRepository.getGoat(goatId)
            .filterNotNull()
            .map {
                GoatDetailsUiState(
                    goatDetails = it.goat.toGoatDetails(),
                    goatVaccinations = it.vaccinations.sortedBy { vac -> vac.date },
                    goatSicknesses = it.sicknesses,
                    goatMilkYields = it.milkYields
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GoatDetailsUiState()
            )

    var vaccinationUiState by mutableStateOf(
        VaccinationUiState(
            vaccinationDetails = VaccinationDetails(
                goatId = goatId
            )
        )
    )
        private set

    var sicknessUiState by mutableStateOf(SicknessUiState(sicknessDetails = SicknessDetails(goatId = goatId)))
        private set

    suspend fun deleteGoat() {
        goatRepository.deleteGoat(uiState.value.goatDetails.toGoat())
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
        updateVaccinationUiState(vaccinationDetails = VaccinationDetails(goatId = goatId))


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
            val sickness =
                sicknessTypesList.value.sicknessTypesList.find { it.id == sickness.sicknessTypeId }
            updateSicknessUiState(
                sickness.toSicknessDetails().copy(sicknessName = sickness?.name ?: "")
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
        updateSicknessUiState(sicknessDetails = SicknessDetails(goatId = goatId))

    //Sicknesses


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GoatDetailsUiState(
    val goatDetails: GoatDetails = GoatDetails(),
    val goatVaccinations: List<Vaccination> = emptyList(),
    val goatSicknesses: List<Sickness> = emptyList(),
    val goatMilkYields: List<MilkYield> = emptyList(),
    val selectedVaccinationId: UUID = UUID.randomUUID(),
    val selectedSicknessId: UUID = UUID.randomUUID()
)

data class SicknessTypesUiState(val sicknessTypesList: List<SicknessType> = emptyList())

//Vaccinations
data class VaccinationUiState(
    val vaccinationDetails: VaccinationDetails = VaccinationDetails(),
    val isEntryValid: Boolean = false
)

data class VaccinationDetails(
    val id: UUID = UUID.randomUUID(),
    val date: Long = System.currentTimeMillis(),
    val sicknessName: String = "",
    val goatId: UUID = UUID.randomUUID(),
    val sicknessTypeId: Int = 0,
    val medication: String = "",
)

fun VaccinationDetails.toVaccination() = Vaccination(
    id = id,
    sicknessTypeId = sicknessTypeId,
    animalId = goatId,
    medication = medication,
    date = date
)

fun Vaccination.toVaccinationDetails() = VaccinationDetails(
    id = id,
    sicknessTypeId = sicknessTypeId,
    goatId = animalId,
    medication = medication,
    date = date
)

//Sicknesses
data class SicknessUiState(
    val sicknessDetails: SicknessDetails = SicknessDetails(),
    val isEntryValid: Boolean = false
)

data class SicknessDetails(
    val id: UUID = UUID.randomUUID(),
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long? = null,
    val sicknessName: String = "",
    val goatId: UUID = UUID.randomUUID(),
    val sicknessTypeId: Int = 0,
)

fun SicknessDetails.toSickness() = Sickness(
    id = id,
    startDate = startDate,
    endDate = endDate,
    sicknessTypeId = sicknessTypeId,
    animalId = goatId,
)

fun Sickness.toSicknessDetails() = SicknessDetails(
    id = id,
    startDate = startDate,
    endDate = endDate,
    sicknessTypeId = sicknessTypeId,
    goatId = animalId,
)