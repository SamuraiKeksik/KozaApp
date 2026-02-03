package com.example.app_features.animals.chickens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.Gender
import com.example.app_data.animals.chickens.ChickenBreed
import com.example.app_data.animals.chickens.ChickenRepository
import com.example.app_data.animals.chickens.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import kotlin.Boolean

@HiltViewModel
class ChickenEntryViewModel @Inject constructor(
    private val chickenRepository: ChickenRepository
) : ViewModel() {
    var chickenUiState by mutableStateOf(ChickenUiState())
        private set

    fun updateUiState(chickenDetails: ChickenDetails) {
        chickenUiState =
            ChickenUiState(chickenDetails = chickenDetails, isEntryValid = validateInput(chickenDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            chickenRepository.insertChicken(chickenUiState.chickenDetails.toChicken())
        }
    }

    private fun validateInput(uiState: ChickenDetails = chickenUiState.chickenDetails): Boolean {
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

data class ChickenUiState(
    val chickenDetails: ChickenDetails = ChickenDetails(),
    val isEntryValid: Boolean = false
)

data class ChickenDetails(
    val id: UUID = UUID.randomUUID(),
    val motherId: UUID? = null,
    val motherName: String? = null,
    val fatherId: UUID? = null,
    val fatherName: String? = null,
    val name: String = "",
    val gender: Gender = Gender.UNKNOWN,
    val breed: ChickenBreed = ChickenBreed.OTHER,
    val status: Status = Status.OTHER,
    val weight: String = "0",
    val birthDate: LocalDate = LocalDate.now(),
    val description: String = "",

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
    //val serverId: String? = null,
    )