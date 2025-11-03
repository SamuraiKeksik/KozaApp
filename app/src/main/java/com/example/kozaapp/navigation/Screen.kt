package com.example.kozaapp.navigation

import androidx.annotation.StringRes
import com.example.kozaapp.R


 object Screens{
    val list = listOf(
        GoatsScreen.Goats,
        GoatsScreen.GoatCreation,
        GoatsScreen.GoatDetails,
        GoatsScreen.GoatEdit,
    )
}

sealed class AnimalsScreen (val route: String){
    object Animals: AnimalsScreen(route = "animals")
}

sealed class AuthScreen(val route: String){
    object Greetings: AuthScreen(route = "greetings")
    object Login: AuthScreen(route = "login")
    object Registration1: AuthScreen(route = "registration1")
    object Registration2: AuthScreen(route = "registration2")
    object Registration3: AuthScreen(route = "registration3")
    object PasswordRecovery1: AuthScreen(route = "password_recovery1")
    object PasswordRecovery2: AuthScreen(route = "password_recovery2")
    object PasswordRecovery3: AuthScreen(route = "password_recovery3")
}

sealed class GoatsScreen(
    val route: String,
    @StringRes val title: Int
){
    object Goats: GoatsScreen(
        route = "animals/goats",
        title = R.string.goats_label,
        )
    object GoatDetails: GoatsScreen(
        route = "animals/goats/details/{id}",
        title = R.string.goat_details_screen_label,
        ){
        fun passId(id: Int): String{
            return "animals/goats/details/${id}"
        }
    }
    object GoatEdit: GoatsScreen(
        route = "animals/goats/edit/{id}",
        title = R.string.goat_edit_screen_label,
    ){
        fun passId(id: Int): String{
            return "animals/goats/edit/${id}"
        }
    }
    object GoatCreation: GoatsScreen(
        route = "animals/goats/creation",
        title = R.string.goat_creation_screen_label
    )

}



sealed class CowsScreen(val route: String){
    object Cows: CowsScreen(route = "animals/goats")
    object CowDetails: CowsScreen(route = "animals/cows/details/{id}")
    object CowEdit: CowsScreen(route = "animals/cows/edit/{id}")
    object CowCreation: CowsScreen(route = "animals/cows/creation")
}