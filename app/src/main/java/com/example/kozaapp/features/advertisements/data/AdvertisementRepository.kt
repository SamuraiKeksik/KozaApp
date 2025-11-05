package com.example.kozaapp.features.advertisements.data

import com.example.kozaapp.features.advertisements.data.model.Advertisement
import com.example.kozaapp.features.advertisements.data.schemas.GetAdvertisementsRequest
import com.example.kozaapp.features.animals.goats.data.GoatRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AdvertisementRepository @Inject constructor(
    private val localDataSource: AdvertisementLocalDataSource,
    private val remoteDataSource: AdvertisementRemoteDataSource,
) {
    fun getAllAdvertisementsStream(): Flow<List<Advertisement>> =
        localDataSource.getAllAdvertisements()

    fun getAdvertisementStream(id: String): Flow<Advertisement?> =
        localDataSource.getAdvertisement(id)

    suspend fun copyAdvertisementsFromServer(
        user_id: String? = null,
        category_id: String? = null,
        subcategory_id: String? = null,
        limit: Int = 30,
        offset: Int = 0, // Смещение: количество объявлений, которые нужно пропустить
    ) {
        try {
            val getAdvertisementsRequest = GetAdvertisementsRequest(
                user_id = user_id,
                category_id = category_id,
                subcategory_id = subcategory_id,
                limit = limit,
                offset = offset,
            )
            val remoteAdvertisementsList = remoteDataSource.getAdvertisementsList(
                getAdvertisementsRequest
            )
            localDataSource.insertAdvertisementList(remoteAdvertisementsList)
        } catch (e: Exception) {
            //ToDo: Сделать обработку ошибки
        }
    }
}