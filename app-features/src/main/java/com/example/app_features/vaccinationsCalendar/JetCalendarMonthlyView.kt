package com.example.app_features.vaccinationsCalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun JetCalendarMonthlyView(
  jetMonth: JetMonth,
  onDateSelected: (JetDay) -> Unit,
  selectedDate: JetDay,
  vaccinationDays: List<LocalDate>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .padding(start = 4.dp, end = 4.dp),
    verticalArrangement = Arrangement.SpaceAround,
  ) {
    jetMonth.monthWeeks.forEach { week ->
      JetCalendarWeekView(
        modifier = Modifier.fillMaxWidth(),
        week = week,
        onDateSelected = onDateSelected,
        selectedDates = selectedDate,
        vaccinationDays = vaccinationDays,
      )
      Spacer(modifier = Modifier.padding(vertical = 2.dp))
    }
  }
}


@Composable
fun WeekNames(dayOfWeek: DayOfWeek) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    dayNames(dayOfWeek = dayOfWeek).forEach {
      Box(
        modifier = Modifier
          .padding(2.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = it, modifier = Modifier.padding(2.dp),
          textAlign = TextAlign.Center,
        )
      }

    }
  }
}