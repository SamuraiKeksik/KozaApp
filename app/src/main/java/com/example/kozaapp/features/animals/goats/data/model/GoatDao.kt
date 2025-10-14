package com.example.kozaapp.features.animals.goats.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kozaapp.features.animals.model.Goat
import kotlinx.coroutines.flow.Flow

@Dao
interface GoatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(goat: Goat)

    @Update
    suspend fun update(goat: Goat)

    @Delete
    suspend fun delete(goat: Goat)

    @Query("SELECT * FROM goats WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllGoats(): Flow<List<Goat>>

    @Query("SELECT * FROM goats WHERE id = :id AND isDeleted = 0")
    fun getGoat(id: Int): Flow<Goat?>

}