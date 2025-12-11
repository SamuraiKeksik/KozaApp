package com.example.advertisements

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