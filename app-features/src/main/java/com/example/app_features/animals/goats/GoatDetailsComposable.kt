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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_data.animals.goats.Gender
import com.example.app_data.animals.goats.GoatEntity
import com.example.app_features.ExpandLabel
import com.example.app_features.R
import java.util.UUID

@Composable
fun GoatDetailsComposable(
    goatEntity: GoatEntity,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onParentInfoClick: (UUID) -> Unit, //UUID родителя
    onParentAddClick: (UUID, Gender) -> Unit,  //UUID ребенка
    onChildrenInfoClick: (UUID) -> Unit, ////UUID родителя
) {
    var expanded by remember { mutableStateOf(true) }
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
            label = stringResource(R.string.goat_details_screen_label),
            expanded = expanded,
            onExpandClick = { expanded = !expanded },
            onActionClick = onEditClick,
            imageVector = Icons.Filled.Mode,
        )
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                GoatDetailsRow(
                    labelResID = R.string.goat_name_label,
                    goatDetail = goatEntity.name,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_gender_label,
                    goatDetail = goatEntity.gender.toString(),
                    //goatDetail = stringResource(goat.gender.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.breed_label,
                    goatDetail = goatEntity.breed.toString(),
                    //goatDetail = stringResource(goat.breed.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.status_label,
                    goatDetail = goatEntity.status.toString(),
                    //goatDetail = stringResource(goat.status.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_weight_label,
                    goatDetail = goatEntity.weight.toString(),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_birth_date_label,
                    goatDetail = goatEntity.birthDate ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_description_label,
                    goatDetail = goatEntity.description ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                Row(modifier = modifier) {
                    Text(text = stringResource(R.string.mother))
                    Spacer(modifier = Modifier.weight(1f))
                    if (goatEntity.motherId != null) {
                        //Text(text = goatEntity.mother.name, fontWeight = FontWeight.Bold
                        Text("Mother")
                        IconButton(onClick = { onParentInfoClick(goatEntity.motherId!!) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        Button(onClick = { onParentAddClick(goatEntity.id, Gender.FEMALE) }) {
                            Text(text = stringResource(R.string.add_mother))
                        }
                    }
                }
                Row(modifier = modifier) {
                    Text(text = stringResource(R.string.father))
                    Spacer(modifier = Modifier.weight(1f))
                    if (goatEntity.fatherId != null) {
                        //Text(text = goatEntity.mother.name, fontWeight = FontWeight.Bold
                        Text("Father")
                        IconButton(onClick = { onParentInfoClick(goatEntity.motherId!!) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        Button(onClick = { onParentAddClick(goatEntity.id, Gender.MALE) }) {
                            Text(text = stringResource(R.string.add_father))
                        }
                    }
                }
                Row(modifier = modifier) {
                    Text(text = stringResource(R.string.children))
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { onChildrenInfoClick(goatEntity.id) }) {
                        Text(text = stringResource(R.string.children_count, 0))
                    }

                }
            }
        }
    }
}

@Composable
private fun GoatDetailsRow(
    @StringRes labelResID: Int,
    goatDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = goatDetail, fontWeight = FontWeight.Bold)
    }
}