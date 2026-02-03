package com.example.app_features.animals.chickens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.chickens.ChickenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChickenChildrenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chickenRepository: ChickenRepository,
) : ViewModel() {
    private val chickenId: UUID = UUID.fromString(checkNotNull(savedStateHandle["id"]))

    val chickenChildrenUiState: StateFlow<ChickensUiState> =
        chickenRepository.getChickenChildren(chickenId).map { ChickensUiState(
            chickensList = it, isLoading = false)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ChickensUiState(),
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


