package com.example.app_features.animals.chickens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.chickens.ChickenEntity
import com.example.app_data.animals.chickens.ChickenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChickensViewModel @Inject constructor(
    chickenRepository: ChickenRepository
): ViewModel() {
    val chickensUiState: StateFlow<ChickensUiState> =
        chickenRepository.chickensList.map { ChickensUiState(it, isLoading = false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ChickensUiState(),
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ChickensUiState(
    val chickensList: List<ChickenEntity> = listOf(),
    val isLoading: Boolean = true
)