package com.example.app_features.animals.chickens

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
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun ChickenDetailsComposable(
    chickenDetails: ChickenDetails,
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
            label = stringResource(R.string.chicken_details_screen_label),
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
                ChickenDetailsRow(
                    label = "Кличка:",
                    chickenDetail = chickenDetails.name,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                ChickenDetailsRow(
                    label = "Пол:",
                    chickenDetail = stringResource(chickenDetails.gender.valueRes),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                ChickenDetailsRow(
                    label = "Порода:",
                    chickenDetail = stringResource(chickenDetails.breed.valueRes),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                ChickenDetailsRow(
                    label = "Статус:",
                    chickenDetail = stringResource(chickenDetails.status.valueRes),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                ChickenDetailsRow(
                    label = "Вес:",
                    chickenDetail = chickenDetails.weight.toString() + " кг",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                ChickenDetailsRow(
                    label = "Дата рождения:",
                    chickenDetail = chickenDetails.birthDate.format(formatter) ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                ChickenDetailsRow(
                    label = "Описание:",
                    chickenDetail = chickenDetails.description ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                Row(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.mother))
                    if (chickenDetails.motherId != null) {
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Text(chickenDetails.motherName!!)
                        IconButton(onClick = { onParentInfoClick(chickenDetails.motherId) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        if(canEdit){
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = { onParentAddClick(chickenDetails.id, Gender.FEMALE)
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
                    if (chickenDetails.fatherId != null) {
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Text(chickenDetails.fatherName!!)
                        IconButton(onClick = { onParentInfoClick(chickenDetails.fatherId) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        if(canEdit){
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = { onParentAddClick(chickenDetails.id, Gender.MALE) }) {
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
                    Button(onClick = { onChildrenInfoClick(chickenDetails.id) }) {
                        Text(text = stringResource(R.string.children))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ChickenDetailsRow(
    label: String,
    chickenDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = label)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
        Text(text = chickenDetail)
    }
}
