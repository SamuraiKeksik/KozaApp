package com.example.app_features.dictionary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_data.animals.AnimalType
import com.example.app_data.dictionary.ArticleCategory
import com.example.app_data.dictionary.ArticleEntity
import com.example.app_data.dictionary.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class DictionaryArticlesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    val selectedAnimalType: AnimalType =
        AnimalType.valueOf(checkNotNull(savedStateHandle["animal_type"]))
    val selectedArticleCategory: ArticleCategory =
        ArticleCategory.valueOf(checkNotNull(savedStateHandle["article_category"]))

    val articlesUiState: StateFlow<ArticlesUiState> =
        dictionaryRepository.getArticlesByCategoryAndAnimalType(
            animalType = selectedAnimalType,
            articleCategory = selectedArticleCategory
        ).map {
            ArticlesUiState(
                articlesList = it,
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ArticlesUiState(),
        )

    fun selectArticle(article: ArticleEntity) {
        articlesUiState.value.selectedArticle = article
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class ArticlesUiState(
    val articlesList: List<ArticleEntity> = listOf(),
    var selectedArticle: ArticleEntity? = null,
    val isLoading: Boolean = true
)