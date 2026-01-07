package com.example.app_features.dictionary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.app_data.animals.AnimalType
import com.example.app_data.dictionary.ArticleCategory
import com.example.app_data.dictionary.ArticleEntity
import com.example.app_data.dictionary.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class DictionaryArticlesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    val selectedAnimalType: AnimalType  = AnimalType.valueOf(checkNotNull(savedStateHandle["animal_type"]))
    val selectedArticleCategory: ArticleCategory  = ArticleCategory.valueOf(checkNotNull(savedStateHandle["article_category"]))

//    var articlesUiState: StateFlow<ArticlesUiState> =
//        dictionaryRepository.getArticles(savedStateHandle[DICTIONARY_ARTICLE_TYPE]!!).map {}
//
//
//    var sicknessTypesList: StateFlow<SicknessTypesUiState> =
//        animalsRepository.sicknessTypesList.map {
//            SicknessTypesUiState(it)
//        }.stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//            SicknessTypesUiState()
//        )
}

data class ArticlesUiState(
    val articles: List<ArticleEntity>? = null,
    val isLoading: Boolean = true,
)
