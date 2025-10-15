package com.example.kozaapp.features.animals.goats.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kozaapp.features.animals.goats.data.GoatRepository
import com.example.kozaapp.features.animals.model.Breed
import com.example.kozaapp.features.animals.model.Gender
import com.example.kozaapp.features.animals.model.Goat
import com.example.kozaapp.features.animals.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

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
    val id: Int = 0,
    val name: String = "",
    val gender: String = Gender.UNKNOWN.toString(),
    val birthDate: String? = LocalDate.now().toString(),
    val description: String = "",
    val breed: String = Breed.OTHER.toString(),
    val status: String = Status.OTHER.toString(),
    val weight: String = "0",
)

fun GoatDetails.toGoat(): Goat {

    val enumGender = runCatching {
        Gender.valueOf(gender.uppercase())
    }.getOrDefault(Gender.UNKNOWN)

    val enumBreed = runCatching {
        Breed.valueOf(breed.uppercase())
    }.getOrDefault(Breed.OTHER)

    val enumStatus = runCatching {
        Status.valueOf(status.uppercase())
    }.getOrDefault(Status.OTHER)


    return Goat(
        id = id,
        name = name,
        gender = enumGender,
        birthDate = birthDate,
        description = description,
        breed = enumBreed,
        status = enumStatus,
        weight = weight.toIntOrNull() ?: 0,
    )
}

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
    breed = breed.toString(),
    status = status.toString(),
    weight = weight.toString(),
)