package com.example.kozaapp.features.animals.model


data class Goat(
    val id: String,
    val name: String,
    val gender: Gender,
    val birthDate: String,
    val chip: String,
    val breed: String,
    val purpose: String,
    val description: String,
    val lambings: List<Okot>,
    val milk_yield: String,
    val parents: String,
    val children: String,
)

enum class Gender() {
    Male,
    Female,
    Unknown,
}

class Okot() {}

object GoatsRepository {
    val goats = listOf(
        Goat(
            "",
            "Маруська",
            Gender.Female,
            "12.05.2019",
            "",
            "",
            "Племя",
            "То да сё",
            listOf(Okot()),
            "",
            "",
            "",
        ),
        Goat(
            "",
            "Маруська",
            Gender.Female,
            "12.05.2019",
            "",
            "",
            "Племя",
            "То да сё",
            listOf(Okot()),
            "",
            "",
            "",
        ),
        Goat(
            "",
            "Маруська",
            Gender.Female,
            "12.05.2019",
            "",
            "",
            "Племя",
            "То да сё",
            listOf(Okot()),
            "",
            "",
            "",
        ),
    )
}
