package com.example.app_data.animals

import androidx.annotation.StringRes
import com.example.app_data.R

enum class Gender(
    @StringRes val valueRes: Int
) {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    UNKNOWN(R.string.gender_unknown);

    companion object {
        fun valuesList(): List<Gender> = entries
    }
}