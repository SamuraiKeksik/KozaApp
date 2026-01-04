package com.example.app_features.animals.goats

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoatChildrenScreen(
    navigateToGoatDetails: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoatChildrenViewModel = hiltViewModel(),
) {
    val goatsUiState by viewModel.goatChildrenUiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoatsBody(
            goatsList = goatsUiState.goatsList,
            onGoatClick = {
                navigateToGoatDetails(it)
            },
            modifier = modifier.fillMaxSize(),
        )
    }

}
