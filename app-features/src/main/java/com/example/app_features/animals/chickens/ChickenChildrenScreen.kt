package com.example.app_features.animals.chickens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_features.R
import java.util.UUID


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChickenChildrenScreen(
    navigateToChickenDetails: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChickenChildrenViewModel = hiltViewModel(),
) {
    val chickensUiState by viewModel.chickenChildrenUiState.collectAsState()

    if(chickensUiState.isLoading) {
        Box(
            Modifier.fillMaxSize().padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ChickensBody(
                chickensList = chickensUiState.chickensList,
                onChickenClick = {
                    navigateToChickenDetails(it)
                },
                modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_medium)),
            )
        }
    }


}
