package com.example.app_data.animals.goats

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
interface GoatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goatEntity: GoatEntity)
    @Update
    suspend fun update(goatEntity: GoatEntity)
    @Delete
    suspend fun delete(goatEntity: GoatEntity)
    @Query("SELECT * FROM goats WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllGoats(): Flow<List<GoatEntity>>
    @Query("SELECT * FROM goats WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllGoatsModels(): Flow<List<GoatModel>>
    @Transaction
    @Query("SELECT * FROM goats WHERE id = :id AND isDeleted = 0")
    fun getGoatModel(id: UUID): Flow<GoatModel?>

    @Query("SELECT Name FROM goats WHERE id = :id")
    suspend fun getGoatName(id: UUID): String?
    @Query("SELECT Gender FROM goats WHERE id = :id")
    suspend fun getGoatGender(id: UUID): String

//    @Query("SELECT * FROM goats WHERE isDeleted = 1")
//    suspend fun getDeletedGoats(): List<Goat>
//
//    @Query("SELECT * FROM goats WHERE isEdited = 1")
//    suspend fun getEditedGoats(): List<Goat>
//    @Query("SELECT id FROM goats WHERE serverId = :serverId")
//    suspend fun getGoatLocalIdByServerId(serverId: String): Int?


}