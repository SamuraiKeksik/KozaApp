package com.example.kozaapp.features.advertisements.data

import com.example.kozaapp.features.advertisements.data.model.Advertisement
import com.example.kozaapp.features.advertisements.data.model.AdvertisementDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdvertisementLocalDataSource @Inject constructor(
    private val advertisementDao: AdvertisementDao
) {
    suspend fun insertAdvertisementList(advertisementList: List<Advertisement>) =
        advertisementList.forEach { advertisement ->
            advertisementDao.insert(advertisement)
        }

    suspend fun deleteAllAdvertisements() =
        advertisementDao.deleteAllAdvertisements()

    fun getAllAdvertisements(): Flow<List<Advertisement>> =
        advertisementDao.getAllAdvertisements()

    fun getAdvertisement(id: String): Flow<Advertisement?> =
        advertisementDao.getAdvertisement(id)
}