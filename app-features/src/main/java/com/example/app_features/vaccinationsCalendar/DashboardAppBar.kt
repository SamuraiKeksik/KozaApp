package com.example.app_features.vaccinationsCalendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//import dev.baseio.googlecalendar.commonui.theme.GoogleCalendarColorProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAppBar(toggleDrawer: () -> Unit, onToggleMonth: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            CalendarMonthPicker {
                onToggleMonth.invoke()
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.FilterList, contentDescription = null)
            }
        },
    )
}


@Composable
fun CalendarMonthPicker(onToggleMonth: () -> Unit) {
    Row(Modifier.clickable {
        onToggleMonth.invoke()
    }, verticalAlignment = Alignment.CenterVertically) {
        Text("February")
        Icon(
            Icons.Filled.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
