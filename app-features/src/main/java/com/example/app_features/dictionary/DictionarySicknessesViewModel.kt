package com.example.app_features.dictionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.SicknessType
import com.example.app_data.dictionary.DictionaryRepository
import com.example.app_features.animals.goats.GoatsUiState
import com.example.app_features.animals.goats.SicknessUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DictionarySicknessesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    val selectedAnimalType: AnimalType =
        AnimalType.valueOf(checkNotNull(savedStateHandle["animal_type"]))
    val sicknessesUiState: StateFlow<SicknessesUiState> =
        dictionaryRepository.getSicknessesTypesByAnimalType(selectedAnimalType).map {
            SicknessesUiState(sicknessesTypesList = it, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SicknessesUiState(),
        )

    fun selectSicknessType(sicknessType: SicknessType) {
        sicknessesUiState.value.selectedSicknessType = sicknessType
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SicknessesUiState(
    val sicknessesTypesList: List<SicknessType> = listOf(),
    var selectedSicknessType: SicknessType? = null,
    val isLoading: Boolean = true
)

