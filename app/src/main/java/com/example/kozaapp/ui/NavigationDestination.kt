package com.example.kozaapp.ui

import androidx.annotation.StringRes

interface NavigationDestination {
    val route: String

    val titleRes: Int
    val showBottomBar: Boolean
}