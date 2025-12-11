//package com.example.network
//
//import com.example.kozaapp.features.animals.model.Goat
//
//data class GoatRequest(
//    val name: String,
//    val gender: String,
//    val breed: String,
//    val status: String,
//    val weight: Int?,
//    val birth_date: String?,
//    val description: String?,
//)
//
//fun Goat.toGoatRequest(): GoatRequest {
//    return GoatRequest(
//        name = this.name,
//        gender = this.gender.toString(),
//        breed = this.breed.toString(),
//        status = this.status.toString(),
//        weight = this.weight,
//        birth_date = this.birthDate,
//        description = this.description,
//    )
//}