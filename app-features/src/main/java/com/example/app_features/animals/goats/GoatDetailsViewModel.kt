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

    var sicknessTypesList: StateFlow<SicknessTypesUiState> = animalsRepository.sicknessTypesList.map {
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

    var vaccinationUiState by mutableStateOf(VaccinationUiState(
            vaccinationDetails = VaccinationDetails(goatId = goatId)
    ))
        private set


    fun updateVaccinationUiState(vaccinationDetails: VaccinationDetails){
        vaccinationUiState = VaccinationUiState(
            vaccinationDetails = vaccinationDetails,
            isEntryValid =
                    vaccinationDetails.date.isNotBlank() &&
                    vaccinationDetails.sicknessName.isNotBlank() &&
                    vaccinationDetails.sicknessTypeId != 0 &&
                    vaccinationDetails.medication.isNotBlank()
            )
    }

    suspend fun insertVaccination() {
        if (vaccinationUiState.isEntryValid) {
            animalsRepository.insertVaccination(vaccinationUiState.vaccinationDetails.toVaccination())
        }
    }

    suspend fun deleteGoat() {
        goatRepository.deleteGoat(uiState.value.goatDetails.toGoat())
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class GoatDetailsUiState(
    val goatDetails: GoatDetails = GoatDetails(),
    val goatVaccinations: List<Vaccination> = emptyList(),
    val goatSicknesses: List<Sickness> = emptyList(),
    val goatMilkYields: List<MilkYield> = emptyList(),
    )

data class VaccinationUiState(
    val vaccinationDetails: VaccinationDetails = VaccinationDetails(),
    val isEntryValid: Boolean = false
)

fun VaccinationDetails.toVaccination() = Vaccination(
    id = UUID.randomUUID(),
    sicknessTypeId = sicknessTypeId,
    animalId = goatId,
    medication = medication,
    date = Date(date)
    //TODo: исправить парсинг даты - из-за него крашится приложеине
)

data class SicknessTypesUiState(val sicknessTypesList: List<SicknessType> = emptyList())

public data class VaccinationDetails(
    val date: String = "",
    val sicknessName: String = "",
    val goatId: UUID = UUID.randomUUID(),
    val sicknessTypeId: Int = 0,
    val medication: String = "",
)
