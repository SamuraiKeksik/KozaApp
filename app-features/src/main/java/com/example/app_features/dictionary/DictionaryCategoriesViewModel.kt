package com.example.app_features.dictionary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.AnimalType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class DictionaryCategoriesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var categoriesUiState by mutableStateOf(CategoriesUiState(
        selectedAnimalType = AnimalType.valueOf(checkNotNull(savedStateHandle["animal_type"])),
    ))
        private set

}

data class CategoriesUiState(
    val selectedAnimalType: AnimalType = AnimalType.UNKNOWN,
)

