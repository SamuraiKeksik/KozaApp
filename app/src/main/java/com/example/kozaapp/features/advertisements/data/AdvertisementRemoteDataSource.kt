package com.example.kozaapp.features.advertisements.data

import com.example.kozaapp.data.network.ApiService
import com.example.kozaapp.features.advertisements.data.model.Advertisement
import com.example.kozaapp.features.advertisements.data.schemas.GetAdvertisementsRequest
import com.example.kozaapp.features.animals.goats.data.schemas.GoatResponse
import retrofit2.HttpException
import javax.inject.Inject

class AdvertisementRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAdvertisementsList(getAdvertisementsRequest: GetAdvertisementsRequest)
    : List<Advertisement> {
        val response = apiService.getAdvertisements(getAdvertisementsRequest)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw HttpException(response) //ToDo: сделать нормальное исключение
        }
    }
}