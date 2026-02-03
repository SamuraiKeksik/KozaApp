package com.example.app_features.animals.chickens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.chickens.ChickenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChickenEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chickenRepository: ChickenRepository
) : ViewModel() {

    var chickenUiState by mutableStateOf(ChickenUiState())
        private set

    private val chickenId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

    init {
        viewModelScope.launch {
            chickenUiState = chickenRepository.getChickenModel(chickenId)
                .filterNotNull()
                .first()
                .chicken
                .toChickenUiState(true)
        }
    }

    suspend fun updateChicken() {
        if (validateInput(chickenUiState.chickenDetails)) {
            chickenRepository.updateChicken(chickenUiState.chickenDetails.toChicken())
        }
    }

    fun updateUiState(chickenDetails: ChickenDetails) {
        chickenUiState =
            ChickenUiState(chickenDetails = chickenDetails, isEntryValid = validateInput(chickenDetails))
    }

    private fun validateInput(uiState: ChickenDetails = chickenUiState.chickenDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}

