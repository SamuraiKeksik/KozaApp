package com.example.app_features.vaccinationsCalendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(
  ExperimentalMaterial3Api::class,
  ExperimentalFoundationApi::class
)
@Composable
fun CalendarEventsCards() {
  val listState = rememberLazyListState()
  LazyColumn(
    state = listState
  ) {
    for (item in 0..30) {
      if (item % 10 == 0) {
        stickyHeader {
          CalendarCardHeader(isAccepted = item % 2 == 0, showDate = true)
        }
      } else {
        item {
          CalendarCardHeader(isAccepted = item % 2 == 0, showDate = false)
        }
      }
    }
  }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarCardHeader(isAccepted: Boolean, showDate: Boolean) {
  Row(
    modifier = Modifier
      .padding(4.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(Modifier.alpha(if (showDate) 1f else 0f)) {
      DateHeaderItem()
    }
    EventCardInternal(
      isAccepted, Modifier
        .weight(1f)
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventCardInternal(isAccepted: Boolean, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(4.dp)
    ,
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
      EventText(
        text = "Планируется прививка от болезни 'Столбняк'",
        style = vaccinationTextStyle(isAccepted),
      )

      EventText(
        text = "Козы",
        style = vaccinationTextStyle(isAccepted),
      )
    }
  }
}

@Composable
fun DateHeaderItem() {
  Column(Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = "Пон",
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
        text = "1",
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


