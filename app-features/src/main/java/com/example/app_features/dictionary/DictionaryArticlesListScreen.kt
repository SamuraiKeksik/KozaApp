package com.example.app_features.dictionary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.app_data.dictionary.ArticleCategory
import com.example.app_data.dictionary.ArticleEntity
import com.example.app_features.EmptyScreenFiller
import com.example.app_features.R

@Composable
fun DictionaryArticlesListScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryArticlesListViewModel = hiltViewModel(),
) {
    var dialogRequired by rememberSaveable { mutableStateOf(false) }
    val articlesUiState = viewModel.articlesUiState.collectAsState().value
    if (articlesUiState.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
    } else {
        if (articlesUiState.articlesList.isEmpty()) {
            EmptyScreenFiller(
                header = R.string.advices_missing,
                text = R.string.advices_will_automaticly_load,
                image = R.drawable.missing_article
            )
        } else
            LazyColumn(
                modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                items(articlesUiState.articlesList, key = { it.id }) {
                    ArticleItem(
                        article = it,
                        onClick = {
                            viewModel.selectArticle(it)
                            dialogRequired = true
                        }
                    )
                }
            }
    }
    DictionaryArticleDialog(
        showDialog = dialogRequired,
        onDismiss = { dialogRequired = false },
        title = stringResource(R.string.advice),
        header = articlesUiState.selectedArticle?.name ?: "",
        animalType = stringResource(R.string.animal_type_label, articlesUiState.selectedArticle?.animalType ?: ""),
        category = stringResource(R.string.category_label, articlesUiState.selectedArticle?.category ?: ""),
        text = articlesUiState.selectedArticle?.description ?: "",
    )


}

@Composable
fun ArticleItem(article: ArticleEntity, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            val articleIcon = {
                when (article.category) {
                    ArticleCategory.FEEDING -> Icons.Default.Fastfood
                    ArticleCategory.BREEDING -> Icons.Default.QueryStats
                    else -> Icons.Default.QuestionMark
                }
            }
            Icon(
                imageVector = articleIcon(),
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp),
                contentDescription = null,
            )
            Column {
                Text(
                    text = article.name, style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = article.category.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.8f)
                )
            }
        }
    }
}