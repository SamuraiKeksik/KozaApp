package com.example.app_features.animals.cows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.Gender
import com.example.app_data.animals.cows.CowBreed
import com.example.app_data.animals.cows.CowRepository
import com.example.app_data.animals.cows.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import kotlin.Boolean

@HiltViewModel
class CowEntryViewModel @Inject constructor(
    private val cowRepository: CowRepository
) : ViewModel() {
    var cowUiState by mutableStateOf(CowUiState())
        private set

    fun updateUiState(cowDetails: CowDetails) {
        cowUiState =
            CowUiState(cowDetails = cowDetails, isEntryValid = validateInput(cowDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            cowRepository.insertCow(cowUiState.cowDetails.toCow())
        }
    }

    private fun validateInput(uiState: CowDetails = cowUiState.cowDetails): Boolean {
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

data class CowUiState(
    val cowDetails: CowDetails = CowDetails(),
    val isEntryValid: Boolean = false
)

data class CowDetails(
    val id: UUID = UUID.randomUUID(),
    val motherId: UUID? = null,
    val motherName: String? = null,
    val fatherId: UUID? = null,
    val fatherName: String? = null,
    val name: String = "",
    val gender: Gender = Gender.UNKNOWN,
    val breed: CowBreed = CowBreed.OTHER,
    val status: Status = Status.OTHER,
    val weight: String = "0",
    val birthDate: LocalDate = LocalDate.now(),
    val description: String = "",

    val isEdited: Boolean = false,
    val isDeleted: Boolean = false,
    //val serverId: String? = null,
    )