package com.example.app_features.animals.goats

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
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
import androidx.compose.ui.unit.dp
import com.example.app_data.animals.Gender
import com.example.app_features.ExpandLabel
import com.example.app_features.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun GoatDetailsComposable(
    goatDetails: GoatDetails,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onParentInfoClick: (UUID) -> Unit, //UUID родителя
    onParentAddClick: (UUID, Gender) -> Unit,  //UUID ребенка
    onChildrenInfoClick: (UUID) -> Unit, ////UUID родителя
    canEdit: Boolean,                   //Можно ли менять информацию
) {
    var expanded by remember { mutableStateOf(true) }
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Card(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
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
            label = stringResource(R.string.goat_details_screen_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = onEditClick,
            imageVector = Icons.Filled.Mode,
            canEdit = canEdit
        )
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                GoatDetailsRow(
                    label = "Кличка:",
                    goatDetail = goatDetails.name,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    label = "Пол:",
                    goatDetail = stringResource(goatDetails.gender.valueRes),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    label = "Порода:",
                    goatDetail = stringResource(goatDetails.breed.valueRes),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    label = "Статус:",
                    goatDetail = stringResource(goatDetails.status.valueRes),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    label = "Вес:",
                    goatDetail = goatDetails.weight.toString() + " кг",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    label = "Дата рождения:",
                    goatDetail = goatDetails.birthDate.format(formatter) ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    label = "Описание:",
                    goatDetail = goatDetails.description ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                Row(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.mother))
                    if (goatDetails.motherId != null) {
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Text(goatDetails.motherName!!)
                        IconButton(onClick = { onParentInfoClick(goatDetails.motherId) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        if(canEdit){
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = { onParentAddClick(goatDetails.id, Gender.FEMALE)
                                }) {
                                Text(text = stringResource(R.string.add_mother))
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        else{
                            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                            Text(text = stringResource(R.string.unknown))
                        }

                    }
                }
                Row(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.father))
                    if (goatDetails.fatherId != null) {
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Text(goatDetails.fatherName!!)
                        IconButton(onClick = { onParentInfoClick(goatDetails.fatherId) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        if(canEdit){
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = { onParentAddClick(goatDetails.id, Gender.MALE) }) {
                                Text(text = stringResource(R.string.add_father))
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        else{
                            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                            Text(text = stringResource(R.string.unknown))
                        }

                    }
                }
                Row(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { onChildrenInfoClick(goatDetails.id) }) {
                        Text(text = stringResource(R.string.children))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun GoatDetailsRow(
    label: String,
    goatDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = label)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
        Text(text = goatDetail)
    }
}
