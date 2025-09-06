package com.example.kozaapp.mainApp.data

import androidx.annotation.DrawableRes

data class AnimalsCommonInfo (
    val specie: String,
    val quantity: Int,
    @DrawableRes val background: Int,
)