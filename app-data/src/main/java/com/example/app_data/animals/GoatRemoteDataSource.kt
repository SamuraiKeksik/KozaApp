package com.example.app_data.animals

//import com.example.kozaapp.data.network.ApiService
//import com.example.kozaapp.features.animals.goats.data.schemas.GoatDeletionResponse
//import com.example.kozaapp.features.animals.goats.data.schemas.GoatRequest
//import com.example.kozaapp.features.animals.goats.data.schemas.GoatResponse
//import retrofit2.HttpException
import javax.inject.Inject

class GoatRemoteDataSource @Inject constructor(
    //private val apiService: ApiService
) {
//    suspend fun getGoatsList(): List<GoatResponse> {
//        val response = apiService.getGoatsList()
//        if (response.isSuccessful) {
//            return response.body() ?: emptyList()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun createGoat(goatRequest: GoatRequest): GoatResponse? {
//        val response = apiService.createGoat(goatRequest)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun updateGoat(goatServerId: String, goatRequest: GoatRequest): GoatResponse? {
//        val response = apiService.updateGoat(goatServerId, goatRequest)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun deleteGoat(goatServerId: String): GoatDeletionResponse? {
//        val response = apiService.deleteGoat(goatServerId)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }

}