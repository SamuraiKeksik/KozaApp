package com.example.app_features.dictionary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.app_features.R


@Composable
fun DictionaryArticleDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    header: String,
    animalType: String,
    category: String,
    text: String,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            Card{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_medium),
                        dimensionResource(id = R.dimen.padding_small),
                        dimensionResource(id = R.dimen.padding_medium),
                        dimensionResource(id = R.dimen.padding_small),
                    )
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        modifier = Modifier.padding(0.dp),
                        onClick = { onDismiss() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .padding(
                            dimensionResource(id = R.dimen.padding_medium),
                            dimensionResource(id = R.dimen.padding_small),
                            dimensionResource(id = R.dimen.padding_medium),
                            dimensionResource(id = R.dimen.padding_medium),
                        )
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,

                ) {

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(header, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Text(
                        text = animalType,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = category,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier.padding(
                            0.dp,
                            dimensionResource(id = R.dimen.padding_small),
                            0.dp,
                            dimensionResource(id = R.dimen.padding_small),

                        ),
                        text = text, fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Button(onClick = onDismiss) {
                        Text(stringResource(R.string.close))
                    }
                }
            }

        }
    }
}