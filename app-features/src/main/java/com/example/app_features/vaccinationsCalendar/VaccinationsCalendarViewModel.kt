package com.example.app_features.vaccinationsCalendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.AnimalsRepository
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.goats.GoatRepository
import com.example.app_data.dictionary.DictionaryRepository
import com.example.app_features.animals.goats.GoatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class VaccinationsCalendarViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val goatsRepository: GoatRepository,
) : ViewModel() {
    var uiState by mutableStateOf(VaccinationsCalendarUiState())
        private set

    fun nextMonth(){
        val currentMonth = uiState.currentMonth
        uiState = uiState.copy(
            currentMonth = JetMonth.current(currentMonth.startDate.plusMonths(1))
        )
    }
    fun previousMonth(){
        val currentMonth = uiState.currentMonth
        uiState = uiState.copy(
            currentMonth = JetMonth.current(currentMonth.startDate.minusMonths(1))
        )
    }

    private fun getVaccinations() = animalsRepository.getVaccinations()
}

data class VaccinationsCalendarUiState(
    val selectedDay: String = "",
    val currentMonth: JetMonth = JetMonth.current(),
    val vaccinations: List<Vaccination> = emptyList(),
    val selectedAnimalTypes: List<AnimalType> = emptyList(),
    val isLoading: Boolean = false,
)

data class AnimalVaccinationDayDetails(
    val date: String,
    val name: String,
    val animalType: AnimalType,
    val vaccinations: List<Vaccination>,
)