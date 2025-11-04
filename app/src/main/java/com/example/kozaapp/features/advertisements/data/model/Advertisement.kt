package com.example.kozaapp.features.advertisements.data.model

import androidx.annotation.StringRes
import com.example.kozaapp.R

data class Advertisement(
    val user_id: String,
    val title: String,
    val description: String,
    val price: Float,

    val category_id: String, //uuid.UUID,
    val subcategory_id: String, //uuid.UUID,

    val location: String,
    val latitude: Float,
    val longitude: Float,
    val contact_name: String,
    val contact_phone: String,
    val date_posted: String, //datetime
    val date_expires: String, //datetime
    val is_active: Boolean,
    val images: List<String>,
    val type: AdvertisementType
)

enum class AdvertisementType(@StringRes val labelResId: Int) {
    BUY(R.string.buy),
    SELL(R.string.sell) ;

}