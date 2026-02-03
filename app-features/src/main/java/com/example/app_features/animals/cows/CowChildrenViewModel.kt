package com.example.app_features.animals.cows

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.cows.CowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CowChildrenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cowRepository: CowRepository,
) : ViewModel() {
    private val cowId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

    val cowChildrenUiState: StateFlow<CowsUiState> =
        cowRepository.getCowChildren(cowId).map { CowsUiState(
            cowsList = it, isLoading = false)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = CowsUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


