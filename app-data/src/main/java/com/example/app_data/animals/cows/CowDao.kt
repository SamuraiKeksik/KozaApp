package com.example.app_data.animals.cows

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
interface CowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cowEntity: CowEntity)
    @Update
    suspend fun update(cowEntity: CowEntity)
    @Delete
    suspend fun delete(cowEntity: CowEntity)
    @Query("SELECT * FROM cows WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllCows(): Flow<List<CowEntity>>
    @Query("SELECT * FROM cows WHERE isDeleted = 0 ORDER BY name ASC")
    suspend fun getCowsModelsList(): List<CowModel>
    @Transaction
    @Query("SELECT * FROM cows WHERE id = :id AND isDeleted = 0")
    fun getCowModel(id: UUID): Flow<CowModel?>

    @Query("SELECT Name FROM cows WHERE id = :id")
    suspend fun getCowName(id: UUID): String?
    @Query("SELECT Gender FROM cows WHERE id = :id")
    suspend fun getCowGender(id: UUID): String

//    @Query("SELECT * FROM cows WHERE isDeleted = 1")
//    suspend fun getDeletedCows(): List<Cow>
//
//    @Query("SELECT * FROM cows WHERE isEdited = 1")
//    suspend fun getEditedCows(): List<Cow>
//    @Query("SELECT id FROM cows WHERE serverId = :serverId")
//    suspend fun getCowLocalIdByServerId(serverId: String): Int?


}