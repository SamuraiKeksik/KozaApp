package com.example.kozaapp.features.advertisements.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AdvertisementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(advertisement: Advertisement)

    @Query("DELETE FROM advertisements")
    suspend fun deleteAllAdvertisements()

    @Query("SELECT * FROM advertisements ORDER BY date_posted ASC")
    fun getAllAdvertisements(): Flow<List<Advertisement>>

    @Query("SELECT * FROM advertisements WHERE id = :id")
    fun getAdvertisement(id: String): Flow<Advertisement?>
}