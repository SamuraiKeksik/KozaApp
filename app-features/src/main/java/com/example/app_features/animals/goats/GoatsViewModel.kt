package com.example.app_features.animals.goats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_data.animals.goats.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GoatsViewModel @Inject constructor(
    goatRepository: GoatRepository
): ViewModel() {
    val goatsUiState: StateFlow<GoatsUiState> =
        goatRepository.goatsList.map { GoatsUiState(it, isLoading = false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GoatsUiState(),
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GoatsUiState(
    val goatsList: List<GoatEntity> = listOf(),
    val isLoading: Boolean = true
)