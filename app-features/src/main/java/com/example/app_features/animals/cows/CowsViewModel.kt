package com.example.app_features.animals.cows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.cows.CowEntity
import com.example.app_data.animals.cows.CowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CowsViewModel @Inject constructor(
    cowRepository: CowRepository
): ViewModel() {
    val cowsUiState: StateFlow<CowsUiState> =
        cowRepository.cowsList.map { CowsUiState(it, isLoading = false) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CowsUiState(),
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class CowsUiState(
    val cowsList: List<CowEntity> = listOf(),
    val isLoading: Boolean = true
)