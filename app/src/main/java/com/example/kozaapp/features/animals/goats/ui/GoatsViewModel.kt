package com.example.kozaapp.features.animals.goats.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kozaapp.features.animals.goats.data.GoatsDataSource
import com.example.kozaapp.features.animals.model.Goat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GoatsViewModel @Inject constructor(
    goatsDataSource: GoatsDataSource
): ViewModel() {
    val goatsUiState: StateFlow<GoatsUiState> =
        goatsDataSource.getAllGoatsStream().map { GoatsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = GoatsUiState(),
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GoatsUiState(val goatsList: List<Goat> = listOf())