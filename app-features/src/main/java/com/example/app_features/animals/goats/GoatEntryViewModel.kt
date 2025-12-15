package com.example.app_features.animals.goats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.goats.Breed
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.GoatRepository
import com.example.app_data.animals.goats.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import kotlin.Boolean

@HiltViewModel
class GoatEntryViewModel @Inject constructor(
    private val goatRepository: GoatRepository
) : ViewModel() {
    var goatUiState by mutableStateOf(GoatUiState())
        private set

    fun updateUiState(goatDetails: GoatDetails) {
        goatUiState =
            GoatUiState(goatDetails = goatDetails, isEntryValid = validateInput(goatDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            goatRepository.insertGoat(goatUiState.goatDetails.toGoat())
        }
    }

    private fun validateInput(uiState: GoatDetails = goatUiState.goatDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}

data class GoatUiState(
    val goatDetails: GoatDetails = GoatDetails(),
    val isEntryValid: Boolean = false
)

data class GoatDetails(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val gender: String = Gender.UNKNOWN.toString(),
    val breed: String = Breed.OTHER.toString(),
    val status: String = Status.OTHER.toString(),
    val weight: String = "0",
    val birthDate: String? = LocalDate.now().toString(),
    val description: String = "",

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
    //val serverId: String? = null,
    )