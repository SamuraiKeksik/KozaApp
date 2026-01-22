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
                    labelResID = R.string.goat_name_label,
                    goatDetail = goatDetails.name,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_gender_label,
                    goatDetail = goatDetails.gender.toString(),
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
                    goatDetail = goatDetails.breed.toString(),
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
                    goatDetail = goatDetails.status.toString(),
                    //goatDetail = stringResource(goat.status.labelResId),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_weight_label,
                    goatDetail = goatDetails.weight.toString(),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_birth_date_label,
                    goatDetail = goatDetails.birthDate ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                GoatDetailsRow(
                    labelResID = R.string.goat_description_label,
                    goatDetail = goatDetails.description ?: "",
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                Row(modifier = modifier) {
                    Text(text = stringResource(R.string.mother))
                    Spacer(modifier = Modifier.weight(1f))
                    if (goatDetails.motherId != null) {
                        //Text(text = goatEntity.mother.name, fontWeight = FontWeight.Bold
                        Text(goatDetails.motherName!!)
                        IconButton(onClick = { onParentInfoClick(goatDetails.motherId) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        if(canEdit){
                            Button(onClick = { onParentAddClick(goatDetails.id, Gender.FEMALE) }) {
                                Text(text = stringResource(R.string.add_mother))
                            }
                        }
                        else{
                            Text(text = stringResource(R.string.missing))
                        }

                    }
                }
                Row(modifier = modifier) {
                    Text(text = stringResource(R.string.father))
                    Spacer(modifier = Modifier.weight(1f))
                    if (goatDetails.fatherId != null) {
                        //Text(text = goatEntity.mother.name, fontWeight = FontWeight.Bold
                        Text(goatDetails.fatherName!!)
                        IconButton(onClick = { onParentInfoClick(goatDetails.fatherId) })
                        {
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "Mother")
                        }
                    } else {
                        if(canEdit){
                            Button(onClick = { onParentAddClick(goatDetails.id, Gender.MALE) }) {
                                Text(text = stringResource(R.string.add_father))
                            }
                        }
                        else{
                            Text(text = stringResource(R.string.missing))
                        }

                    }
                }
                Row(modifier = modifier) {
                    Text(text = stringResource(R.string.children))
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { onChildrenInfoClick(goatDetails.id) }) {
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