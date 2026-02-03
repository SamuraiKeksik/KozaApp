package com.example.app_data.animals.cows

//import com.example.kozaapp.data.network.ApiService
//import com.example.kozaapp.features.animals.cows.data.schemas.CowDeletionResponse
//import com.example.kozaapp.features.animals.cows.data.schemas.CowRequest
//import com.example.kozaapp.features.animals.cows.data.schemas.CowResponse
//import retrofit2.HttpException
import javax.inject.Inject

class CowRemoteDataSource @Inject constructor(
    //private val apiService: ApiService
) {
//    suspend fun getCowsList(): List<CowResponse> {
//        val response = apiService.getCowsList()
//        if (response.isSuccessful) {
//            return response.body() ?: emptyList()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun createCow(cowRequest: CowRequest): CowResponse? {
//        val response = apiService.createCow(cowRequest)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun updateCow(cowServerId: String, cowRequest: CowRequest): CowResponse? {
//        val response = apiService.updateCow(cowServerId, cowRequest)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
//
//    suspend fun deleteCow(cowServerId: String): CowDeletionResponse? {
//        val response = apiService.deleteCow(cowServerId)
//        if (response.isSuccessful) {
//            return response.body()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }

}