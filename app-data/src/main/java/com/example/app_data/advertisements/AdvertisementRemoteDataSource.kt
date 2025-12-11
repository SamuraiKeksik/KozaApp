package com.example.advertisements


import javax.inject.Inject

class AdvertisementRemoteDataSource @Inject constructor(
   // private val apiService: ApiService
) {
//    suspend fun getAdvertisementsList(
//        userId: String?,
//        categoryId: String?,
//        subcategoryId: String?,
//        limit: Int,
//        offset: Int,
//    )
//    : List<Advertisement> {
//        val response = apiService.getAdvertisements(
//            userId = userId,
//            categoryId = categoryId,
//            subcategoryId = subcategoryId,
//            limit = limit,
//            offset = offset,
//        )
//        if (response.isSuccessful) {
//            return response.body() ?: emptyList()
//        } else {
//            throw HttpException(response) //ToDo: сделать нормальное исключение
//        }
//    }
}