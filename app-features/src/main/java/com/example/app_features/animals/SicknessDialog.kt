package com.example.app_features.animals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.app_data.animals.SicknessType
import com.example.app_features.R
import com.example.app_features.animals.goats.SicknessDetails
import java.text.SimpleDateFormat

@Composable
fun SicknessDialog(
    label: String,
    sicknessDetails: SicknessDetails,
    sicknessTypesList: List<SicknessType>,
    isEntryValid: Boolean,
    onAddConfirm: () -> Unit,
    onAddCancel: () -> Unit,
    onStartDateFocused: () -> Unit,
    onStartDateUnFocused: () -> Unit,
    onEndDateFocused: () -> Unit,
    onEndDateUnFocused: () -> Unit,
    onValueChange: (SicknessDetails) -> Unit,
    dateFormat: SimpleDateFormat,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = { /* Do nothing */ }) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { onAddCancel() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.padding_medium),
                        0.dp,
                        dimensionResource(id = R.dimen.padding_medium),
                        dimensionResource(id = R.dimen.padding_medium),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column {
                    OutlinedTextField(
                        value = dateFormat.format(sicknessDetails.startDate),
                        onValueChange = { onValueChange(sicknessDetails.copy(startDate = it.toLong())) },
                        label = { Text(stringResource(R.string.start_date)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) onStartDateFocused() else onStartDateUnFocused()
                            },
                        readOnly = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(onClick = onStartDateFocused) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = null
                                )
                            }
                        },
                    )
                    OutlinedTextField(
                        value = if (sicknessDetails.endDate == null) "" else dateFormat.format(
                            sicknessDetails.endDate
                        ),
                        onValueChange = { onValueChange(sicknessDetails.copy(endDate = it.toLongOrNull())) },
                        label = { Text(stringResource(R.string.end_date)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) onEndDateFocused() else onEndDateUnFocused()
                            },
                        readOnly = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(onClick = onEndDateFocused) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = null
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    SicknessTypeSelector(
                        sicknessTypesList = sicknessTypesList,
                        selectedSicknessName = sicknessDetails.sicknessName,
                        onSicknessUpdate = { id, name ->
                            onValueChange(
                                sicknessDetails.copy(
                                    sicknessTypeId = id,
                                    sicknessName = name
                                )
                            )
                        },
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onAddCancel() },
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                    TextButton(
                        onClick = { onAddConfirm() },
                        enabled = isEntryValid,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}