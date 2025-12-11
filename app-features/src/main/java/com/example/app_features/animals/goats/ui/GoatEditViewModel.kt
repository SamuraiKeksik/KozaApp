package com.example.animals.goats.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kozaapp.features.animals.goats.data.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoatEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatRepository: GoatRepository
) : ViewModel() {

    var goatUiState by mutableStateOf(GoatUiState())
        private set

    private val goatId: Int = checkNotNull(savedStateHandle["id"])

    init {
        viewModelScope.launch {
            goatUiState = goatRepository.getGoatStream(goatId)
                .filterNotNull()
                .first()
                .toGoatUiState(true)
        }
    }

    suspend fun updateGoat() {
        if (validateInput(goatUiState.goatDetails)) {
            goatRepository.updateGoat(goatUiState.goatDetails.toGoat())
        }
    }

    fun updateUiState(goatDetails: GoatDetails) {
        goatUiState =
            GoatUiState(goatDetails = goatDetails, isEntryValid = validateInput(goatDetails))
    }

    private fun validateInput(uiState: GoatDetails = goatUiState.goatDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && gender.isNotBlank()
        }
    }
}
