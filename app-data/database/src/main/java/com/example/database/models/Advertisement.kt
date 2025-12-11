package com.example.database.models

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kozaapp.R

@Entity(tableName = "advertisements")
data class Advertisement(
    @PrimaryKey
    val id: String, //UUID
    val user_id: String,
    val title: String,
    val description: String,
    val price: Float,

    val category_id: Int,
    val subcategory_id: Int,

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