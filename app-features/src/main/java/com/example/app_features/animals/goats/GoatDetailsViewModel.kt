package com.example.app_features.animals.goats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GoatDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatRepository: GoatRepository,
) : ViewModel() {
    private val goatId: UUID = checkNotNull(savedStateHandle["id"])

    var uiState: StateFlow<GoatDetailsUiState> =
        goatRepository.getGoat(goatId)
            .filterNotNull()
            .map {
                GoatDetailsUiState(goatDetails = it.toGoatDetails())
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
    val goatDetails: GoatDetails = GoatDetails()
)
