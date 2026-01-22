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
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.app_features.R
import com.example.app_features.animals.goats.MilkYieldDetails
import com.example.app_features.animals.goats.toLocalDate
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@Composable
fun MilkYieldDialog(
    label: String,
    milkYieldDetails: MilkYieldDetails,
    isEntryValid: Boolean,
    onAddConfirm: () -> Unit,
    onAddCancel: () -> Unit,
    onDateFocused: () -> Unit,
    onDateUnFocused: () -> Unit,
    onValueChange: (MilkYieldDetails) -> Unit,
    dateFormat: DateTimeFormatter,
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
                        value = milkYieldDetails.date.toLocalDate().format(dateFormat),
                        onValueChange = {
                            //onValueChange(milkYieldDetails.copy(date = LocalDa it))
                                        },
                        label = { Text(stringResource(R.string.date)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) onDateFocused() else onDateUnFocused()
                            },
                        readOnly = true,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        trailingIcon = {
                            IconButton(onClick = onDateFocused) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = null
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    OutlinedTextField(
                        value = milkYieldDetails.amount.toString(),
                        onValueChange = {
                            onValueChange(
                                milkYieldDetails.copy(
                                    amount = it.replace(",", ".")
                                )
                            )
                        },
                        label = { Text(stringResource(R.string.litres_of_milk)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.WaterDrop,
                                contentDescription = null
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