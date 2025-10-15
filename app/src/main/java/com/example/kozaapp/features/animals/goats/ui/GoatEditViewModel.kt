package com.example.kozaapp.features.animals.goats.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kozaapp.features.animals.goats.data.GoatsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoatEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatsDataSource: GoatsDataSource
) : ViewModel() {

    var goatUiState by mutableStateOf(GoatUiState())
        private set

    private val goatId: Int = checkNotNull(savedStateHandle[GoatEditDestination.goatIdArg])

    init {
        viewModelScope.launch {
            goatUiState = goatsDataSource.getGoatStream(goatId)
                .filterNotNull()
                .first()
                .toGoatUiState(true)
        }
    }

    suspend fun updateGoat() {
        if (validateInput(goatUiState.goatDetails)) {
            goatsDataSource.updateGoat(goatUiState.goatDetails.toGoat())
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
