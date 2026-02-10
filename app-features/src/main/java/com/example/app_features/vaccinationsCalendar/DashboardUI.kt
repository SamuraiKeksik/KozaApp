package com.example.app_features.vaccinationsCalendar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.AnimalType
import com.example.app_features.R
import com.example.app_features.monthPicker.MonthPickerBottomSheet
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun DashboardUI(
    viewModel: VaccinationsCalendarViewModel = hiltViewModel(),
) {
    var monthPickerRequired by remember { mutableStateOf(false) }
    var filterRequired by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            DashboardAppBar(
                onMonthSelect = { monthPickerRequired = true },
                onPreviousMonth = {
                    viewModel.previousMonth()
                    viewModel.viewModelScope.launch {
                        viewModel.getVaccinations()
                    }
                },
                onNextMonth = {
                    viewModel.nextMonth()
                    viewModel.viewModelScope.launch {
                        viewModel.getVaccinations()
                    }
                },
                onFilterClick = {
                    filterRequired = !filterRequired
                },
                jetMonth = viewModel.uiState.currentMonth
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { padding ->
        GoogleCalendarSurface(
            modifier = Modifier.padding(padding)
        ) {
            if (monthPickerRequired) {
                MonthPickerBottomSheet(
                    showYear = true,
                    onDismiss = {
                        monthPickerRequired = false
                    },
                    onUpdateMonth = { month, year ->
                        val date = LocalDate.of(year, month + 1, 1)
                        val jetMonth = JetMonth.current(date)
                        viewModel.updateCurrentMonth(jetMonth)
                        viewModel.viewModelScope.launch {
                            viewModel.getVaccinations()
                        }

//                        val calendar = Calendar.getInstance()
//                        calendar.set(Calendar.MONTH, month)
//                        calendar.set(Calendar.YEAR, year)
//                    monthValue = " ${
//                        SimpleDateFormat("MMMM").format(calendar.time)
//                    } - $year"
                    },
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = filterRequired,
                    enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                ) {
                    FilterChipRow(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.viewModelScope.launch {
                                viewModel.updateSelectedAnimalTypes(it)
                            }
                        }
                    )
                }
                DashboardMonthView(
                    Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    jetMonth = viewModel.uiState.currentMonth,
                    onDateSelect = {
                        viewModel.updateSelectedDate(it)
                        viewModel.viewModelScope.launch {
                            val index =
                                viewModel.findEventIndexByDate(viewModel.uiState.selectedDay)
                            if (index != -1)
                                lazyListState.scrollToItem(index)
                        }
                    }
                )
                Box {
                    CalendarEventsCards(
                        vaccinationEvents = viewModel.uiState.vaccinationsEvents,
                        lazyListState = lazyListState
                    )
                }
            }
        }
    }

}


@Composable
fun FilterChipForAnimalType(
    onClick: (AnimalType) -> Unit,
    animalType: AnimalType
) {
    var selected by remember { mutableStateOf(true) }
    FilterChip(
        selected = selected,
        onClick = {
            selected = !selected
            onClick(animalType)
        },
        label = { Text(stringResource(animalType.valueRes)) },
        leadingIcon =
            if (selected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Localized Description",
                        modifier = Modifier.size(FilterChipDefaults.IconSize),
                    )
                }
            } else {
                {

                }
            },
    )
}

@Composable
fun FilterChipRow(
    modifier: Modifier = Modifier,
    onClick: (AnimalType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small), 0.dp, 0.dp, 0.dp)
    ) {
        val list = listOf(AnimalType.GOAT, AnimalType.COW, AnimalType.CHICKEN)
        list.forEach { animalType ->
            FilterChipForAnimalType(
                onClick = onClick,
                animalType = animalType
            )
            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}


