package com.example.advertisements

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/// Local Part
class AdvertisementRepository @Inject constructor(
    private val localDataSource: AdvertisementLocalDataSource,
    private val remoteDataSource: AdvertisementRemoteDataSource,
) {
    fun getAllAdvertisementsStream(): Flow<List<Advertisement>> =
        localDataSource.getAllAdvertisements()

    fun getAdvertisementStream(id: String): Flow<Advertisement?> =
        localDataSource.getAdvertisement(id)

    suspend fun deleteAllAdvertisements(){
        localDataSource.deleteAllAdvertisements()
    }

            /// Remote Part
//    suspend fun copyAdvertisementsFromServer(
//        userId: String? = null,
//        categoryId: String? = null,
//        subcategoryId: String? = null,
//        limit: Int = 30,
//        offset: Int = 0, // Смещение: количество объявлений, которые нужно пропустить
//    ) {
//        try {
//            val remoteAdvertisementsList = remoteDataSource.getAdvertisementsList(
//                userId = userId,
//                categoryId = categoryId,
//                subcategoryId = subcategoryId,
//                limit = limit,
//                offset = offset,
//            )
//            localDataSource.insertAdvertisementList(remoteAdvertisementsList)
//        } catch (e: Exception) {
//            Log.e("Network", e.message ?: "")
//            //ToDo: Сделать обработку ошибки
//        }
//    }

}