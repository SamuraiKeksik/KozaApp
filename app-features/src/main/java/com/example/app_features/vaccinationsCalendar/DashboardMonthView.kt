package com.example.app_features.vaccinationsCalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import java.time.DayOfWeek
import java.time.LocalDate


@Composable
fun DashboardMonthView(
  modifier: Modifier,
  jetMonth: JetMonth,

) {
  GoogleCalendarSurface(modifier = modifier) {
    val selectedDates = rememberSaveable(stateSaver = JetDaySaver) {
      mutableStateOf(JetDay(LocalDate.now(), true))
    }
    Column(Modifier.fillMaxWidth()) {
      WeekNames(DayOfWeek.MONDAY)
      JetCalendarMonthlyView(
        jetMonth = jetMonth,
        onDateSelected = {
          selectedDates.value = it
        },
        selectedDate = selectedDates.value
      )
    }
  }
}

val JetDaySaver =  run {
  val dateKey = "Date"
  val isPartKey = "IsPart"
  mapSaver(
    save = { mapOf(dateKey to it.date, isPartKey to it.isPartOfMonth) },
    restore = { JetDay(it[dateKey] as LocalDate, it[isPartKey] as Boolean)}
  )
}
