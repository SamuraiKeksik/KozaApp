package com.example.app_features.dictionary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import java.util.UUID


@Composable
fun DictionaryArticlesListScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryArticlesListViewModel = hiltViewModel(),
    navigateToArticleScreen: (articleId: UUID) -> Unit,
){

}