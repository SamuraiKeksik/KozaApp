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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.app_features.R

//import dev.baseio.googlecalendar.commonui.theme.GoogleCalendarColorProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardAppBar(
    onMonthSelect: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onFilterClick: () -> Unit,
    jetMonth: JetMonth
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            CalendarMonthPicker(
                onMonthSelect = onMonthSelect,
                onPreviousMonth = onPreviousMonth,
                onNextMonth = onNextMonth,
                jetMonth = jetMonth
            )
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(Icons.Filled.FilterList, contentDescription = null)
            }
        },
    )
}


@Composable
fun CalendarMonthPicker(
    onMonthSelect: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    jetMonth: JetMonth
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                Icons.Filled.ArrowLeft,
                contentDescription = null,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
            )
        }
        Text(
            text = jetMonth.monthYear(),
            modifier = Modifier.clickable {
                onMonthSelect()
            },
        )
        IconButton(onClick = onNextMonth) {
            Icon(
                Icons.Filled.ArrowRight,
                contentDescription = null,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
            )
        }

    }
}
