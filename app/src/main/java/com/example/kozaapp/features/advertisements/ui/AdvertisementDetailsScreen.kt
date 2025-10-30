import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kozaapp.R
import com.example.kozaapp.ui.NavigationDestination

object AdvertisementDetailsDestination: NavigationDestination{
    override val route = "AdvertisementDetailsScreen"
    @StringRes
    override val titleRes = R.string.empty_string
    override val showBottomBar = true
}
@Composable
fun AdvertisementDetailsScreen() {
    val adDetails = AdDetails(
        title = "Комплект для Jetpack Compose разработки",
        price = "99,999 ₽",
        description = "Продается мощный ноутбук с предустановленным Android Studio. Идеально подходит для начинающих и опытных разработчиков на Kotlin. Полностью настроен и готов к работе. Состояние отличное, использовался всего полгода.",
        imageUrls = listOf(
            "https://picsum.photos/id/1018/800/600",
            "https://picsum.photos/id/1025/800/600",
            "https://picsum.photos/id/1027/800/600"
        ),
        userName = "Иван Иванов",
        userPhone = "+7 900 123-45-67"
    )
    // Используем rememberScrollState() и Modifier.verticalScroll() для общей прокрутки всего контента
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Вся страница будет прокручиваться
    ) {
        // 1. Скроллер картинок (Галерея)
        ImageGallery()

        // Дополнительный контейнер для основного контента
        Column(modifier = Modifier.padding(16.dp)) {

            // 2. Цена и Название
            Text(
                text = adDetails.price,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = adDetails.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Разделитель
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(16.dp))

            // 3. Описание
            SectionTitle(title = "Описание")
            Text(
                text = adDetails.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))

            // 4. Информация о Пользователе
            SectionTitle(title = "Продавец")
            UserBlock(userName = adDetails.userName)
            Spacer(modifier = Modifier.height(16.dp))

            // 5. Кнопка "Контакты"
            ContactButton(userPhone = adDetails.userPhone)
            Spacer(modifier = Modifier.height(16.dp)) // Отступ в конце
        }
    }
}

// Композиция 1: Скроллер картинок (Галерея)
@Composable
fun ImageGallery() {
    // LazyRow для горизонтальной прокрутки, только если у нас много картинок
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Фиксированная высота для галереи
    ) {
        items(5) { url ->
            Image(
                painter = painterResource(R.drawable.goats_background),
                contentDescription = null, // Можно добавить описание
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(350.dp) // Ширина для каждого изображения
            )
            // Добавляем небольшой отступ между изображениями
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


// Вспомогательная Композиция: Заголовок секции
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

// Вспомогательная Композиция: Блок Пользователя
@Composable
fun UserBlock(userName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = userName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

// Вспомогательная Композиция: Кнопка Контактов
@Composable
fun ContactButton(userPhone: String) {
    Button(
        onClick = { /* TODO: Логика для совершения звонка или открытия контактов */ },
        modifier = Modifier.fillMaxWidth().height(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call Icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Показать телефон")
    }
}


// ПРЕДПРОСМОТР и Пример использования
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AdDetailPreview() {
    // Оберните в Scaffold для полноценного экрана с TopAppBar (необязательно)
    Scaffold(
        topBar = { TopAppBar(title = { Text("Детали объявления") }) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AdvertisementDetailsScreen()
        }
    }
}

data class AdDetails(
    val title: String,
    val price: String,
    val description: String,
    val imageUrls: List<String>,
    val userName: String,
    val userPhone: String
)