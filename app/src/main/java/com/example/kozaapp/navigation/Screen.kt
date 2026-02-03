package com.example.kozaapp.navigation

import androidx.annotation.StringRes
import com.example.app_data.animals.AnimalType
import com.example.app_data.animals.Gender
import com.example.app_data.dictionary.ArticleCategory
import com.example.kozaapp.R
import java.util.UUID

sealed class Screen(
    val route: String,
    @StringRes val title: Int
) {
    companion object {
        val list: List<Screen> by lazy {
            listOf<Screen>(
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
                GoatsScreen.GoatParentInfo,
                GoatsScreen.GoatChildren,
                GoatsScreen.GoatChildInfo,
                
                CowsScreen.Cows,
                CowsScreen.CowCreation,
                CowsScreen.CowParentAdding,
                CowsScreen.CowDetails,
                CowsScreen.CowEdit,
                CowsScreen.CowParentInfo,
                CowsScreen.CowChildren,
                CowsScreen.CowChildInfo,
                
                ChickensScreen.Chickens,
                ChickensScreen.ChickenCreation,
                ChickensScreen.ChickenParentAdding,
                ChickensScreen.ChickenDetails,
                ChickensScreen.ChickenEdit,
                ChickensScreen.ChickenParentInfo,
                ChickensScreen.ChickenChildren,
                ChickensScreen.ChickenChildInfo,

                DictionaryScreen.Categories,
                DictionaryScreen.Sicknesses,
                DictionaryScreen.ArticlesList,

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

sealed class VaccinationsCalendarScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Calendar : VaccinationsCalendarScreen(
        route = "vaccinations_calendar",
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
    ) {
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
sealed class CowsScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Cows : Screen(
        route = "animals/cows",
        title = R.string.cows_label,
    )

    object CowDetails : Screen(
        route = "animals/cows/details/{id}",
        title = R.string.cow_details_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/cows/details/${id}"
        }
    }

    object CowEdit : Screen(
        route = "animals/cows/edit/{id}",
        title = R.string.cow_edit_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/cows/edit/${id}"
        }
    }

    object CowCreation : Screen(
        route = "animals/cows/creation",
        title = R.string.cow_creation_screen_label
    )

    object CowParentAdding : Screen(
        route = "animals/cows/parent_Adding/{id}/{parent_gender}",
        title = R.string.cow_adding_parent_screen_label
    ) {
        fun passIdWithGender(id: UUID, parentGender: Gender): String {
            return "animals/cows/parent_Adding/${id}/${parentGender}"
        }
    }

    object CowParentInfo : Screen(
        route = "animals/cows/parent_info/{id}",
        title = R.string.cow_parent_info_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/cows/parent_info/${id}"
        }
    }

    object CowChildren : Screen(
        route = "animals/cows/cow_children/{id}",
        title = R.string.cow_children_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/cows/cow_children/${id}"
        }
    }

    object CowChildInfo : Screen(
        route = "animals/cows/cow_child_info/{id}",
        title = R.string.cow_child_info_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/cows/cow_child_info/${id}"
        }
    }

}
sealed class ChickensScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Chickens : Screen(
        route = "animals/chickens",
        title = R.string.chickens_label,
    )

    object ChickenDetails : Screen(
        route = "animals/chickens/details/{id}",
        title = R.string.chicken_details_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/chickens/details/${id}"
        }
    }

    object ChickenEdit : Screen(
        route = "animals/chickens/edit/{id}",
        title = R.string.chicken_edit_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/chickens/edit/${id}"
        }
    }

    object ChickenCreation : Screen(
        route = "animals/chickens/creation",
        title = R.string.chicken_creation_screen_label
    )

    object ChickenParentAdding : Screen(
        route = "animals/chickens/parent_Adding/{id}/{parent_gender}",
        title = R.string.chicken_adding_parent_screen_label
    ) {
        fun passIdWithGender(id: UUID, parentGender: Gender): String {
            return "animals/chickens/parent_Adding/${id}/${parentGender}"
        }
    }

    object ChickenParentInfo : Screen(
        route = "animals/chickens/parent_info/{id}",
        title = R.string.chicken_parent_info_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/chickens/parent_info/${id}"
        }
    }

    object ChickenChildren : Screen(
        route = "animals/chickens/chicken_children/{id}",
        title = R.string.chicken_children_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/chickens/chicken_children/${id}"
        }
    }

    object ChickenChildInfo : Screen(
        route = "animals/chickens/chicken_child_info/{id}",
        title = R.string.chicken_child_info_screen_label,
    ) {
        fun passId(id: UUID): String {
            return "animals/chickens/chicken_child_info/${id}"
        }
    }

}

sealed class DictionaryScreen(
    val route: String,
    @StringRes val title: Int
) {
    object Animals : Screen(
        route = "dictionary/animals",
        title = R.string.dictionary,
    )

    object Categories : Screen(
        route = "dictionary/{animal_type}/categories",
        title = R.string.categories,
    ) {
        fun passAnimal(animalType: AnimalType): String {
            return "dictionary/${animalType}/categories"
        }
    }

    object Sicknesses : Screen(
        route = "dictionary/{animal_type}/sicknesses",
        title = R.string.sicknesses,
    ) {
        fun passAnimalType(animalType: AnimalType): String {
            return "dictionary/${animalType}/sicknesses"
        }
    }

    object ArticlesList : Screen(
        route = "dictionary/{animal_type}/{article_category}/articles",
        title = R.string.advices,
    ) {
        fun passAnimalWithCategory(
            animalType: AnimalType,
            articleCategory: ArticleCategory
        ): String {
            return "dictionary/${animalType}/${articleCategory}/articles"
        }
    }
}

