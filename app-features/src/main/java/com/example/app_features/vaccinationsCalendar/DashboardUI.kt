package com.example.app_features.vaccinationsCalendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun DashboardUI() {
    Scaffold(
        topBar = {
            DashboardAppBar(
                onToggleMonth = {},
                toggleDrawer = {},
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { padding ->
        GoogleCalendarSurface(
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                DashboardMonthView(Modifier.fillMaxWidth().padding(6.dp))
                Box {
                    CalendarEventsCards()
                }
            }
        }
    }

}



