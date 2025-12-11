package com.example.app_data.animals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app_data.animals.Goat
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface GoatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goat: Goat)
    @Update
    suspend fun update(goat: Goat)
    @Delete
    suspend fun delete(goat: Goat)
    @Query("SELECT * FROM goats WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllGoats(): Flow<List<Goat>>
    @Query("SELECT * FROM goats WHERE id = :id AND isDeleted = 0")
    fun getGoat(id: UUID): Flow<Goat?>

//    @Query("SELECT * FROM goats WHERE isDeleted = 1")
//    suspend fun getDeletedGoats(): List<Goat>
//
//    @Query("SELECT * FROM goats WHERE isEdited = 1")
//    suspend fun getEditedGoats(): List<Goat>
//    @Query("SELECT id FROM goats WHERE serverId = :serverId")
//    suspend fun getGoatLocalIdByServerId(serverId: String): Int?


}