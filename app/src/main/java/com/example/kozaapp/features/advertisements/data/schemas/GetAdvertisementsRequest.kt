package com.example.kozaapp.features.advertisements.data.schemas

data class GetAdvertisementsRequest(
    val user_id: String?,
    val category_id: String?,
    val subcategory_id: String?,
    val limit: Int = 30,
    val offset: Int = 0, // Смещение: количество объявлений, которые нужно пропустить
)