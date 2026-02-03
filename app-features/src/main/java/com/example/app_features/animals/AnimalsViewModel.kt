package com.example.app_features.animals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.AnimalsRepository
import com.example.app_features.animals.goats.GoatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val repository: AnimalsRepository
) : ViewModel() {
    var uiState by mutableStateOf(AnimalsUiState())
        private set
    init {
        viewModelScope.launch {
            repository.getAnimalsCountStream(AnimalType.GOAT).collect {
                uiState = uiState.copy(goatsCount = it)
            }
        }
        viewModelScope.launch {
            repository.getAnimalsCountStream(AnimalType.COW).collect {
                uiState = uiState.copy(cowsCount = it)
            }
        }
            viewModelScope.launch {
                repository.getAnimalsCountStream(AnimalType.CHICKEN).collect {
                    uiState = uiState.copy(chickensCount = it)
                }
            }
        }
    }



data class AnimalsUiState(
    val goatsCount: Int = 0,
    val cowsCount: Int = 0,
    val chickensCount: Int = 0,
)