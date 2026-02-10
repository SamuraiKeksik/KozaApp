package com.example.app_features.vaccinationsCalendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.AnimalsRepository
import com.example.app_data.animals.Vaccination
import com.example.app_data.animals.chickens.ChickenRepository
import com.example.app_data.animals.cows.CowRepository
import com.example.app_data.animals.goats.GoatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@HiltViewModel
class VaccinationsCalendarViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val goatsRepository: GoatRepository,
    private val cowsRepository: CowRepository,
    private val chickensRepository: ChickenRepository,
) : ViewModel() {

    var uiState by mutableStateOf(VaccinationsCalendarUiState())
        private set

    init {
        viewModelScope.launch {
            getVaccinations()
        }
    }
    suspend fun getVaccinations() {
        uiState = uiState.copy(vaccinationsEvents = emptyList(), isLoading = true)
        getGoatsVaccinations()
        getCowsVaccinations()
    }

    suspend fun getGoatsVaccinations() {
        val currentMonth = uiState.currentMonth
        val goatsModelsList = goatsRepository.getGoatsModelsList()
        val vaccinationsEventsGoats = goatsModelsList.flatMap { goatModel ->
            goatModel.vaccinations.filter {
                val start = currentMonth.startDate.toEpochDay()
                val end = currentMonth.endDate.toEpochDay()
                it.date in start..end
            }.map { vaccination ->
                AnimalVaccinationEventDetails(
                    date = LocalDate.ofEpochDay(vaccination.date),
                    animalName = goatModel.goat.name,
                    animalType = AnimalType.GOAT,
                    sicknessTypeName = animalsRepository.getSicknessType(vaccination.sicknessTypeId)?.name ?:
                        animalsRepository.getSicknessType(0)!!.name,
                )
            }
        }.sortedBy {
            it.date
        }
        val currentVaccinations = uiState.vaccinationsEvents
        uiState = uiState.copy(vaccinationsEvents = currentVaccinations + vaccinationsEventsGoats, isLoading = false)
    }
    suspend fun getCowsVaccinations() {
        val currentMonth = uiState.currentMonth
        val cowsModelsList = cowsRepository.getCowsModelsList()
        val vaccinationsEventsCows = cowsModelsList.flatMap { cowModel ->
            cowModel.vaccinations.filter {
                val start = currentMonth.startDate.toEpochDay()
                val end = currentMonth.endDate.toEpochDay()
                it.date in start..end
            }.map { vaccination ->
                AnimalVaccinationEventDetails(
                    date = LocalDate.ofEpochDay(vaccination.date),
                    animalName = cowModel.cow.name,
                    animalType = AnimalType.COW,
                    sicknessTypeName = animalsRepository.getSicknessType(vaccination.sicknessTypeId)?.name ?:
                        animalsRepository.getSicknessType(0)!!.name,
                )
            }
        }.sortedBy {
            it.date
        }
        val currentVaccinations = uiState.vaccinationsEvents
        uiState = uiState.copy(vaccinationsEvents = currentVaccinations + vaccinationsEventsCows, isLoading = false)
    }

    fun nextMonth() {
        val currentMonth = uiState.currentMonth
        uiState = uiState.copy(
            currentMonth = JetMonth.current(currentMonth.startDate.plusMonths(1))
        )
    }

    fun previousMonth() {
        val currentMonth = uiState.currentMonth
        uiState = uiState.copy(
            currentMonth = JetMonth.current(currentMonth.startDate.minusMonths(1))
        )
    }



    //private fun getVaccinations() = animalsRepository.getVaccinations()
}

data class VaccinationsCalendarUiState(
    val selectedDay: String = "",
    val currentMonth: JetMonth = JetMonth.current(),
    val vaccinationsEvents: List<AnimalVaccinationEventDetails> = emptyList(),
    val selectedAnimalTypes: List<AnimalType> = listOf(AnimalType.ALL),
    val isLoading: Boolean = false,
)

data class AnimalVaccinationEventDetails(
    val date: LocalDate,
    val animalName: String,
    val animalType: AnimalType,
    val sicknessTypeName: String,
)