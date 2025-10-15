package com.example.kozaapp.features.animals.goats.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kozaapp.features.animals.goats.data.GoatsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GoatDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val goatsDataSource: GoatsDataSource,
) : ViewModel() {
    private val goatId: Int = checkNotNull(savedStateHandle[GoatDetailsDestination.goatIdArg])

    val uiState: StateFlow<GoatDetailsUiState> =
        goatsDataSource.getGoatStream(goatId)
            .filterNotNull()
            .map {
                GoatDetailsUiState(goatDetails = it.toGoatDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GoatDetailsUiState()
            )

    suspend fun deleteGoat() {
        goatsDataSource.deleteGoat(uiState.value.goatDetails.toGoat())
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class GoatDetailsUiState(
    val goatDetails: GoatDetails = GoatDetails()
)
