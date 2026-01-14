package com.example.app_features.vaccinationsCalendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun DashboardUI() {
    GoogleCalendarSurface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DashboardMonthView(Modifier.fillMaxWidth())
            Box {
                CalendarEventsCards()
            }
        }
    }
}



