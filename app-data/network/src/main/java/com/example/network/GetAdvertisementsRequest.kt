package com.example.network

data class GetAdvertisementsRequest(
    val user_id: String?,
    val category_id: Int?,
    val subcategory_id: Int?,
    val limit: Int = 30,
    val offset: Int = 0, // Смещение: количество объявлений, которые нужно пропустить
)