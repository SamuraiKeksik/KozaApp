package com.example.kozaapp.features.advertisements.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kozaapp.R
import com.example.kozaapp.animals.ui.screens.AnimalsScreen
import com.example.kozaapp.ui.NavigationDestination
import com.example.kozaapp.ui.theme.AppTheme

object AdvertisementsCardsDestination: NavigationDestination{
    override val route = "AdvertisementsCardsScreen"
    @StringRes
    override val titleRes = R.string.empty_string
    override val showBottomBar = true
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertisementsCardsScreen(
    navigateToAdvertisementDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
   // viewModel: AdvertisementsViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    var active by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val colors1 = SearchBarDefaults.colors()
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { active = false },
                    expanded = active,
                    onExpandedChange = { },
                    placeholder = { Text("Search") },
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    colors = colors1.inputFieldColors,
                )
            },
            expanded = active,
            onExpandedChange = { },
            modifier = Modifier.fillMaxWidth()
                .padding(5.dp),
            shape = SearchBarDefaults.inputFieldShape,
            colors = colors1,
            tonalElevation = SearchBarDefaults.TonalElevation,
            shadowElevation = SearchBarDefaults.ShadowElevation,
            windowInsets = SearchBarDefaults.windowInsets,
            content = { },
        )
        ScrollableRowExample()
        ProductGridScreen()
    }

}


@Composable
fun ScrollableRowExample() {
    // 1. Подготовьте данные для списка
    val itemsList = remember {
        (1..20).map { "Item $it" } // Список из 20 элементов
    }

    LazyRow(
        // 2. Определите модификаторы для LazyRow
        modifier = Modifier
            .fillMaxWidth() // Растянуть на всю ширину
            .height(60.dp)  // Задайте фиксированную высоту для прокручиваемого ряда
            .padding(vertical = 8.dp)
            .background(Color(0xFFF0F0F0)),

        // 3. Добавьте отступ между элементами (необязательно)
        horizontalArrangement = Arrangement.spacedBy(10.dp),

        // 4. Добавьте отступы по краям (необязательно)
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        // 5. Передайте список и определите вид каждого элемента
        items(20) { item ->
            ItemCard(text = "item")
        }
    }
}

// Композиция для отображения отдельного элемента
@Composable
fun ItemCard(text: String) {
    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 140.dp) // Фиксированный размер карточки
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(text = text)
        }
    }
}

@Composable
fun ProductGridScreen() {

    // Использование LazyVerticalGrid для создания сетки
    LazyVerticalGrid(
        // Определение количества столбцов: Fixed(2) создает две колонки
        // одинаковой ширины.
        columns = GridCells.Fixed(2),

        // Отступы вокруг всей сетки
        contentPadding = PaddingValues(10.dp),

        // Вертикальный и горизонтальный интервал между элементами
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),

        modifier = Modifier.fillMaxSize()
    ) {
        // Использование items для отображения списка
        items(20) { product ->
            ProductCard()
        }
    }
}

@Composable
fun ProductCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Фиксированная высота для карточки продукта
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.goats_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Занимает доступное пространство в Column
            )

            // 2. Цена и Название (занимают нижнюю часть)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "1234",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "product.name",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1 // Ограничиваем название одной строкой
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AdvertisementsCardsScreenPreview() {
    AppTheme {
        AdvertisementsCardsScreen(
           navigateToAdvertisementDetails = {},
        )
    }
}