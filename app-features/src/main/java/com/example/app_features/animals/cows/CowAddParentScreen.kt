package com.example.app_features.animals.cows

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app_features.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CowAddParentScreen(
    navigateBack: () -> Unit,
    //navigateToCowDetails: (UUID) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CowAddParentViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val cowsUiState by viewModel.cowsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var addParentDialogRequired by rememberSaveable{ mutableStateOf(false) }

    if(cowsUiState.isLoading) {
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
            CowsBody(
                cowsList = cowsUiState.cowsList,
                onCowClick = {
                    addParentDialogRequired = true
                    coroutineScope.launch {
                        viewModel.updateParentId(it)
                        viewModel.saveParent()
                    }
                    navigateBack()
                },
                modifier = modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_medium)),
            )
        }
    }


}
