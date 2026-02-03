package com.example.app_features.animals.cows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.cows.CowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CowEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cowRepository: CowRepository
) : ViewModel() {

    var cowUiState by mutableStateOf(CowUiState())
        private set

    private val cowId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

    init {
        viewModelScope.launch {
            cowUiState = cowRepository.getCowModel(cowId)
                .filterNotNull()
                .first()
                .cow
                .toCowUiState(true)
        }
    }

    suspend fun updateCow() {
        if (validateInput(cowUiState.cowDetails)) {
            cowRepository.updateCow(cowUiState.cowDetails.toCow())
        }
    }

    fun updateUiState(cowDetails: CowDetails) {
        cowUiState =
            CowUiState(cowDetails = cowDetails, isEntryValid = validateInput(cowDetails))
    }

    private fun validateInput(uiState: CowDetails = cowUiState.cowDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}

