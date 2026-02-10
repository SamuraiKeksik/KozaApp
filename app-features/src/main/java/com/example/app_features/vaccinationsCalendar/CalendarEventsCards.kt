package com.example.app_features.vaccinationsCalendar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.app_data.animals.AnimalType
import com.example.app_features.EmptyScreenFiller
import com.example.app_features.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun CalendarEventsCards(
    vaccinationEvents: List<AnimalVaccinationEventDetails>,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        state = lazyListState
    ) {
        if (vaccinationEvents.firstOrNull() == null) {
          item{
              EmptyScreenFiller(
                  header = R.string.empty_string,
                  text = R.string.empty_vaccination_calendar_text,
                  image = R.drawable.calendar,
              )
          }
        }
      else{
          var currentDate = LocalDate.MIN
          var iterator = 0
            items(vaccinationEvents, key = { it.vaccinationId }){ event ->
                if (currentDate != event.date) {
                    CalendarCardHeader(
                        isAccepted = iterator++ % 2 == 0,
                        showDate = true,
                        event = event,
                    )
                }else{
                    CalendarCardHeader(
                        isAccepted = iterator++ % 2 == 0,
                        showDate = false,
                        event = event,
                    )
                }
                currentDate = event.date
            }
//            if (currentDate != event.date) {
//              stickyHeader {
//                CalendarCardHeader(
//                  isAccepted = iterator++ % 2 == 0,
//                  showDate = true,
//                  event = event,
//                )
//              }
//                currentDate = event.date
//            } else {
//              item {
//                CalendarCardHeader(
//                  isAccepted = iterator++ % 2 == 0,
//                  showDate = false,
//                  event = event,
//                )
//              }
//            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarCardHeader(
    event: AnimalVaccinationEventDetails,
    isAccepted: Boolean,
    showDate: Boolean
) {
    Row(
        modifier = Modifier
            .padding(4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(Modifier.alpha(if (showDate) 1f else 0f)) {
            DateHeaderItem(event.date)
        }
        EventCardInternal(
            event,
            isAccepted,
            Modifier.weight(1f),

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventCardInternal(event:AnimalVaccinationEventDetails, isAccepted: Boolean, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.onPrimaryContainer //if (isAccepted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isAccepted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
        )

    ) {
        Column(Modifier.padding(8.dp)) {
            val animalTypeText = when (event.animalType) {
                AnimalType.COW -> "У коровы '"
                AnimalType.GOAT -> "У козы '"
                AnimalType.CHICKEN -> "У курицы '"
                else -> "У животного '"
            }
            EventText(
                text = animalTypeText + event.animalName + "' планируется прививка от болезни '" + event.sicknessTypeName + "'",
                style = vaccinationTextStyle(isAccepted),
            )

//            EventText(
//                text = event.animalType.name,
//                style = vaccinationTextStyle(isAccepted),
//            )
        }
    }
}

@Composable
fun DateHeaderItem(date: LocalDate) {
    Column(Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = date.dayOfWeek.getDisplayName(
                java.time.format.TextStyle.SHORT,
                Locale.getDefault() ) ,
            style = TextStyle.Default.copy(
                MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = TextStyle.Default.copy(
                    MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun vaccinationTextStyle(isAccepted: Boolean) =
    if (isAccepted) TextStyle.Default.copy(
        MaterialTheme.colorScheme.onPrimary
    ) else TextStyle.Default.copy(
        MaterialTheme.colorScheme.onPrimaryContainer
    )

@Composable
fun EventText(text: String, style: TextStyle) {
    Text(
        text = text,
        style = style
    )
}


