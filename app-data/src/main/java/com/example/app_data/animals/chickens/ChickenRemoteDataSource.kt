package com.example.app_data.animals.chickens

//import com.example.kozaapp.data.network.ApiService
//import com.example.kozaapp.features.animals.chickens.data.schemas.ChickenDeletionResponse
//import com.example.kozaapp.features.animals.chickens.data.schemas.ChickenRequest
//import com.example.kozaapp.features.animals.chickens.data.schemas.ChickenResponse
//import retrofit2.HttpException
import javax.inject.Inject

class ChickenRemoteDataSource @Inject constructor(
    //private val apiService: ApiService
) {
//    suspend fun getChickensList(): List<ChickenResponse> {
//        val response = apiService.getChickensList()
//        if (response.isSuccessful) {
//            return response.body() ?: emptyList()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun createChicken(chickenRequest: ChickenRequest): ChickenResponse? {
//        val response = apiService.createChicken(chickenRequest)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun updateChicken(chickenServerId: String, chickenRequest: ChickenRequest): ChickenResponse? {
//        val response = apiService.updateChicken(chickenServerId, chickenRequest)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun deleteChicken(chickenServerId: String): ChickenDeletionResponse? {
//        val response = apiService.deleteChicken(chickenServerId)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }

}