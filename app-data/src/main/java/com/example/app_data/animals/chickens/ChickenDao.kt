package com.example.app_data.animals.chickens

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ChickenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chickenEntity: ChickenEntity)
    @Update
    suspend fun update(chickenEntity: ChickenEntity)
    @Delete
    suspend fun delete(chickenEntity: ChickenEntity)
    @Query("SELECT * FROM chickens WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllChickens(): Flow<List<ChickenEntity>>
    @Query("SELECT * FROM chickens WHERE isDeleted = 0 ORDER BY name ASC")
    suspend fun getChickensModelsList(): List<ChickenModel>
    @Transaction
    @Query("SELECT * FROM chickens WHERE id = :id AND isDeleted = 0")
    fun getChickenModel(id: UUID): Flow<ChickenModel?>

    @Query("SELECT Name FROM chickens WHERE id = :id")
    suspend fun getChickenName(id: UUID): String?
    @Query("SELECT Gender FROM chickens WHERE id = :id")
    suspend fun getChickenGender(id: UUID): String

//    @Query("SELECT * FROM chickens WHERE isDeleted = 1")
//    suspend fun getDeletedChickens(): List<Chicken>
//
//    @Query("SELECT * FROM chickens WHERE isEdited = 1")
//    suspend fun getEditedChickens(): List<Chicken>
//    @Query("SELECT id FROM chickens WHERE serverId = :serverId")
//    suspend fun getChickenLocalIdByServerId(serverId: String): Int?


}