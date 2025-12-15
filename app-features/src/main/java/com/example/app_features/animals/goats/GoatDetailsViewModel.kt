package com.example.app_features.animals.goats

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.MilkYield
import com.example.app_data.animals.Sickness
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.goats.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GoatDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatRepository: GoatRepository,
) : ViewModel() {
    private val goatId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

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
