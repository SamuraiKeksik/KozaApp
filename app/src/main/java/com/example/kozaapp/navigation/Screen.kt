package com.example.kozaapp.navigation

import androidx.annotation.StringRes
import com.example.app_data.animals.goats.Gender
import com.example.kozaapp.R
import java.util.UUID

sealed class Screen(
    val route: String,
    @StringRes val title: Int
) {
    companion object {
        val list by lazy {
            listOf(
                AuthScreen.Greetings,
                AuthScreen.Login,
                AuthScreen.PasswordRecovery1,
                AuthScreen.PasswordRecovery2,
                AuthScreen.PasswordRecovery3,
                AuthScreen.Registration1,
                AuthScreen.Registration2,
                AuthScreen.Registration3,

                GoatsScreen.Goats,
                GoatsScreen.GoatCreation,
                GoatsScreen.GoatParentAdding,
                GoatsScreen.GoatDetails,
                GoatsScreen.GoatEdit,

                AdvertisementsScreen.Advertisements,
                AdvertisementsScreen.AdvertisementDetails,
                )
        }
    }
}

sealed class AnimalsScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Animals : AnimalsScreen(
        route = "animals",
        title = R.string.empty_string
    )
}

sealed class AuthScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Greetings : Screen(
        route = "greetings",
        title = R.string.greetings_screen_header
    )

    object Login : Screen(
        route = "login",
        title = R.string.login_label
    )

    object Registration1 : Screen(
        route = "registration1",
        title = R.string.registration_label
    )

    object Registration2 : Screen(
        route = "registration2",
        title = R.string.registration_label
    )

    object Registration3 : Screen(
        route = "registration3",
        title = R.string.registration_label
    )

    object PasswordRecovery1 : Screen(
        route = "password_recovery1",
        title = R.string.password_recovery_label
    )

    object PasswordRecovery2 : Screen(
        route = "password_recovery2",
        title = R.string.password_recovery_label
    )

    object PasswordRecovery3 : Screen(
        route = "password_recovery3",
        title = R.string.password_recovery_label
    )
}

sealed class AdvertisementsScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Advertisements : Screen(
        route = "advertisements",
        title = R.string.advertisements,
    )
    object AdvertisementDetails : Screen(
        route = "advertisements/details/{id}",
        title = R.string.advertisement_details,
    ) {
        fun passId(id: Int): String {
            return "advertisements/details/${id}"
        }
    }
}

sealed class ProfileScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Profile : Screen(
        route = "profile",
        title = R.string.profile,
    )
}
sealed class GoatsScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Goats : Screen(
        route = "animals/goats",
        title = R.string.goats_label,
    )

    object GoatDetails : Screen(
        route = "animals/goats/details/{id}",
        title = R.string.goat_details_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/goats/details/${id}"
        }
    }

    object GoatEdit : Screen(
        route = "animals/goats/edit/{id}",
        title = R.string.goat_edit_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/goats/edit/${id}"
        }
    }

    object GoatCreation : Screen(
        route = "animals/goats/creation",
        title = R.string.goat_creation_screen_label
    )

    object GoatParentAdding : Screen(
        route = "animals/goats/parent_Adding/{id}/{parent_gender}",
        title = R.string.goat_adding_parent_screen_label
    ){
        fun passIdWithGender(id: UUID, parentGender: Gender): String {
            return "animals/goats/parent_Adding/${id}/${parentGender}"
        }
    }

    object GoatParentInfo : Screen(
        route = "animals/goats/parent_info/{id}",
        title = R.string.goat_parent_info_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/goats/parent_info/${id}"
        }
    }
    object GoatChildren : Screen(
        route = "animals/goats/goat_children/{id}",
        title = R.string.goat_children_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/goats/goat_children/${id}"
        }
    }
    object GoatChildInfo : Screen(
        route = "animals/goats/goat_child_info/{id}",
        title = R.string.goat_child_info_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/goats/goat_child_info/${id}"
        }
    }

}


sealed class CowsScreen(val route: String) {
    object Cows : CowsScreen(route = "animals/goats")
    object CowDetails : CowsScreen(route = "animals/cows/details/{id}")
    object CowEdit : CowsScreen(route = "animals/cows/edit/{id}")
    object CowCreation : CowsScreen(route = "animals/cows/creation")
}