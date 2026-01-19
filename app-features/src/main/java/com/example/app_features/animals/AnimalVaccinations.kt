package com.example.app_features.animals

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.app_data.animals.SicknessType
import com.example.app_data.animals.Vaccination
import com.example.app_features.ExpandLabel
import com.example.app_features.R
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.UUID

@Composable
fun AnimalVaccinations(
    vaccinationsList: List<Vaccination>,
    sicknessTypesList: List<SicknessType>,
    onAddClick: () -> Unit,
    onEditClick: (UUID) -> Unit,
    onDeleteClick: (UUID) -> Unit,
    dateFormat: DateTimeFormatter,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    canEdit: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = RoundedCornerShape(10.dp)
            )
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        ExpandLabel(
            label = stringResource(R.string.vaccinations_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = onAddClick,
            imageVector = Icons.Filled.Add,
            canEdit = canEdit,
        )
        if (expanded) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            ) {
                if (vaccinationsList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.empty_vaccinations_list_label),
                            // style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = modifier.heightIn(max = 400.dp),
                        contentPadding = contentPadding,
                    ) {
                        items(items = vaccinationsList.take(10), key = { it.id }) { vaccination ->
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier) {
                                    Text(text = dateFormat.format(vaccination.date))
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                    Text(
                                        text = sicknessTypesList.find { it.id == vaccination.sicknessTypeId }?.name
                                            ?: ""
                                    )
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                                    Text(text = vaccination.medication)
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                }
                                Row(
                                    modifier = Modifier.widthIn(min = 24.dp)
                                ) {
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onEditClick(vaccination.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Mode,
                                            contentDescription = ""
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                                    IconButton(
                                        modifier = Modifier.size(24.dp),
                                        onClick = { onDeleteClick(vaccination.id) }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (vaccinationsList.size > 10) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {}) { Text("Переход на экран прививок") }
                        //ToDo:Переход на экран прививок
                    }
                }
            }
        }
    }
}
