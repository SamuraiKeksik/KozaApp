package com.example.kozaapp.features.animals.goats.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kozaapp.features.animals.goats.data.GoatsRepository
import com.example.kozaapp.features.animals.model.Gender
import com.example.kozaapp.features.animals.model.Goat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoatEntryViewModel @Inject constructor(
    private val goatsRepository: GoatsRepository
) : ViewModel() {

    var goatUiState by mutableStateOf(GoatUiState())
        private set

    fun updateUiState(goatDetails: GoatDetails) {
        goatUiState =
            GoatUiState(goatDetails = goatDetails, isEntryValid = validateInput(goatDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            goatsRepository.insertGoat(goatUiState.goatDetails.toGoat())
        }
    }

    private fun validateInput(uiState: GoatDetails = goatUiState.goatDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && gender.isNotBlank()
        }
    }
}

data class GoatUiState(
    val goatDetails: GoatDetails = GoatDetails(),
    val isEntryValid: Boolean = false
)

data class GoatDetails(
    val id: Int = 0,
    val name: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val description: String = "",
)

fun GoatDetails.toGoat(): Goat = Goat(
    id = id,
    name = name,
    gender = enumValueOf<Gender>(gender),
    birthDate = birthDate,
    description = description,
)

fun Goat.toGoatUiState(isEntryValid: Boolean = false): GoatUiState = GoatUiState(
    goatDetails = this.toGoatDetails(),
    isEntryValid = isEntryValid
)
fun Goat.toGoatDetails(): GoatDetails = GoatDetails(
    id = id,
    name = name,
    gender = gender.toString(),
    birthDate = birthDate,
    description = description,
)