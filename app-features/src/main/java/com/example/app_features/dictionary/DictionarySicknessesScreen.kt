package com.example.app_features.dictionary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.app_data.animals.SicknessType
import com.example.app_features.EmptyScreenFiller
import com.example.app_features.R

@Composable
fun DictionarySicknessesScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionarySicknessesViewModel = hiltViewModel(),
) {
    val sicknessesUiState = viewModel.sicknessesUiState.collectAsState()
    if (sicknessesUiState.value.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else {
        if (sicknessesUiState.value.sicknessesTypesList.isEmpty()) {
            EmptyScreenFiller(
                header = R.string.sicknesses_missing,
                text = R.string.sicknesses_will_automaticly_load,
                image = R.drawable.missing_article
            )
        } else
            LazyColumn(modifier = modifier) {
                items(sicknessesUiState.value.sicknessesTypesList, key = { it.id }) {
                    SicknessTypeItem(it)
                }
            }
    }




}

@Composable
fun SicknessTypeItem(sicknessType: SicknessType) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Sick,
                tint = Color(0xFF3949AB),
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp),
                contentDescription = "Play"
            )
            Column {
                Text(
                    text = sicknessType.name, style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = stringResource(R.string.sickness_types), color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}