package com.example.app_features.animals.goats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.Gender
import com.example.app_data.animals.goats.GoatBreed
import com.example.app_data.animals.goats.GoatRepository
import com.example.app_data.animals.goats.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
            name.isNotBlank() //&& isValidDateFormat(birthDate)

        }
    }

    private fun isValidDateFormat(date: String): Boolean {
        return try{
            DateTimeFormatter.ofPattern("dd.MM.yyyy").parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}

data class GoatUiState(
    val goatDetails: GoatDetails = GoatDetails(),
    val isEntryValid: Boolean = false
)

data class GoatDetails(
    val id: UUID = UUID.randomUUID(),
    val motherId: UUID? = null,
    val motherName: String? = null,
    val fatherId: UUID? = null,
    val fatherName: String? = null,
    val name: String = "",
    val gender: Gender = Gender.UNKNOWN,
    val breed: GoatBreed = GoatBreed.OTHER,
    val status: Status = Status.OTHER,
    val weight: String = "0",
    val birthDate: LocalDate = LocalDate.now(),
    val description: String = "",

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
    //val serverId: String? = null,
    )